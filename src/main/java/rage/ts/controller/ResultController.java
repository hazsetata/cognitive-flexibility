package rage.ts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String CALLBACK_RESULT_INFO = "game";
    private static final String CALLBACK_RESULT_TYPE = "TASKSWITCHING";

    private static final Logger log = LoggerFactory.getLogger(ResultController.class);

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
            if ((CALLBACK_RESULT_INFO.equalsIgnoreCase(result.getInfo().trim())) &&
                    (CALLBACK_RESULT_TYPE.equalsIgnoreCase(result.getTestType().trim()))) {
                log.debug("Final results arrived, notifying callback. Token: {}", callbackSessionTokenHolder.getSessionToken());
                callbackHandler.notifyCallbackForSessionToken(callbackSessionTokenHolder.getSessionToken());
            }
        }

        return aggregateResultService.calculateResult(result);
    }
}
