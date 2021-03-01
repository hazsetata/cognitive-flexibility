package fi.ttl.cognitive.cognitiveflexibilitytstest.controller;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.AuthenticationInformation;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.Participant;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.AuthenticationInformationRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.ParticipantRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.ClientIdGuardService;
import fi.ttl.cognitive.cognitiveflexibilitytstest.session.CallbackSessionTokenHolder;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAuthenticationController {

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private AuthenticationInformationRepository authenticationInformationRepository;

    @Autowired
    private CallbackSessionTokenHolder callbackSessionTokenHolder;

    @Autowired
    private ClientIdGuardService clientIdGuardService;

    @RequestMapping(value = "app/whoami", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Participant getDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return participantRepository.findByUsername(username);
    }

    @RequestMapping(value = "app/auth/{username}", method = RequestMethod.GET)
    public String login(
            @PathVariable String username,
            @RequestParam(value = "callbackToken", required = false) String callbackToken,
            @RequestParam(value = "clientId", required = false) String clientId,
            HttpServletRequest request
    ) {
        return auth(username, "", callbackToken, clientId, "", request);
    }

    @RequestMapping(value = "app/formauth", method = RequestMethod.POST)
    public String performFormAuth(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", defaultValue = "", required = false) String password,
            @RequestParam(value = "callbackToken", required = false) String callbackToken,
            @RequestParam(value = "clientId", required = false) String clientId,
            @RequestParam(value = "metadata", defaultValue = "", required = false) String metadata,
            HttpServletRequest request
    ) {
        return auth(username, password, callbackToken, clientId, metadata, request);
    }

    private boolean authenticationSuccessful(String username, String password, String metadata, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, password);

        if (metadata == null || !metadata.contains(servletContext.getContextPath())) {
            metadata += ";" + servletContext.getContextPath();
        }

        try {
            Authentication auth = authenticationManager.authenticate(token);
            if (!auth.isAuthenticated()) {
                throw new BadCredentialsException("No such user..");
            }

            storeLoginInformation(username, metadata, request);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return true;
        } catch (BadCredentialsException ex) {
        }

        return false;
    }

    private String auth(String username, String password, String callbackToken, String clientId, String metadata, HttpServletRequest request) {
        if (clientIdGuardService.requestAllowed(clientId)) {
            if (authenticationSuccessful(username, password, metadata, request)) {
                if (StringUtils.hasText(callbackToken)) {
                    callbackSessionTokenHolder.setSessionToken(callbackToken);
                }
                return "redirect:/game.html";
            }
        }

        return "redirect:/index.html?error";
    }

    private void storeLoginInformation(String username, String metadata, HttpServletRequest request) {
        Participant p = participantRepository.findByUsernameAndContextPath(username, servletContext.getContextPath());
        if (p == null) {
            throw new IllegalArgumentException("Unable to find " + username + " although (s)he should be present.");
        }

        AuthenticationInformation info = new AuthenticationInformation(p, getDetails(request), metadata);
        authenticationInformationRepository.save(info);
    }

    private String getDetails(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            Object objHeader = headers.nextElement();
            if (!(objHeader instanceof String)) {
                continue;
            }

            String header = (String) objHeader;
            String value = request.getHeader(header);
            sb.append(header.trim()).append("=").append(value).append("\n");
        }

        return sb.toString().trim();
    }
}
