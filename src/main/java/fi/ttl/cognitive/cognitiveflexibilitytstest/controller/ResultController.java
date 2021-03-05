package fi.ttl.cognitive.cognitiveflexibilitytstest.controller;

import fi.ttl.cognitive.cognitiveflexibilitytstest.service.CallbackHandlerService;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.TestResult;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.ParticipantRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.AggregateResultService;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.ResultService;
import fi.ttl.cognitive.cognitiveflexibilitytstest.session.CallbackSessionTokenHolder;
import fi.ttl.cognitive.cognitiveflexibilitytstest.vo.AggregateResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private CallbackHandlerService callbackHandler;

    @RequestMapping(method = RequestMethod.POST, value = "app/result", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<AggregateResultVO> postResult(@RequestBody TestResult result) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            result.setParticipant(participantRepository.findByUsername(username));

            resultService.save(result);
            AggregateResultVO retValue = aggregateResultService.calculateResult(result);

            if (StringUtils.hasText(callbackSessionTokenHolder.getSessionToken())) {
                if ((CALLBACK_RESULT_INFO.equalsIgnoreCase(result.getInfo().trim())) &&
                        (CALLBACK_RESULT_TYPE.equalsIgnoreCase(result.getTestType().trim()))) {
                    log.debug("Final results arrived, notifying callback. Token: {}", callbackSessionTokenHolder.getSessionToken());
                    callbackHandler.notifyCallbackForSessionToken(callbackSessionTokenHolder.getSessionToken());
                }
            }

            return new ResponseEntity<>(retValue, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
