package rage.ts.controller;

import org.springframework.util.StringUtils;
import rage.ts.callback.CallbackHandler;
import rage.ts.service.ResultService;
import rage.ts.domain.TestResult;
import rage.ts.repository.ParticipantRepository;
import rage.ts.service.AggregateResultService;
import rage.ts.session.CallbackSessionTokenHolder;
import rage.ts.vo.AggregateResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultController {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ResultService resultService;
    @Autowired
    private AggregateResultService aggregateResultService;

    @Autowired
    private CallbackSessionTokenHolder callbackSessionTokenHolder;
    @Autowired
    private CallbackHandler callbackHandler;

    @RequestMapping(method = RequestMethod.POST, value = "app/result", consumes = "application/json")
    @ResponseBody
    public AggregateResultVO postResult(@RequestBody TestResult result) {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username != null) {
                result.getParticipant().setUsername(username);
            }
        }

        result.setParticipant(participantRepository.findByUsername(result.getParticipant().getUsername()));

        resultService.save(result);

        if (StringUtils.hasText(callbackSessionTokenHolder.getSessionToken())) {
            callbackHandler.notifyCallbackForSessionToken(callbackSessionTokenHolder.getSessionToken());
        }

        return aggregateResultService.calculateResult(result);
    }
}
