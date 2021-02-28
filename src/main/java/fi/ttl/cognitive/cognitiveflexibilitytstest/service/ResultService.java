package fi.ttl.cognitive.cognitiveflexibilitytstest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.AdditionalKeyPress;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.Participant;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.Reaction;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.TestResult;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.AdditionalKeyPressRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.ParticipantRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.ReactionRepository;
import fi.ttl.cognitive.cognitiveflexibilitytstest.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private AdditionalKeyPressRepository additionalKeyPressRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @Async
    @Transactional(readOnly = false)
    public void save(TestResult result) {
        if (result.getParticipant() == null || result.getParticipant().getUsername() == null) {
            return;
        }

        Participant p = participantRepository.findByUsername(result.getParticipant().getUsername());
        result.setParticipant(p);

        List<AdditionalKeyPress> presses = new ArrayList<>();
        for (AdditionalKeyPress additionalKeyPress : result.getAdditionalKeyPresses()) {
            presses.add(additionalKeyPressRepository.save(additionalKeyPress));
        }
        result.setAdditionalKeyPresses(presses);

        List<Reaction> reactions = new ArrayList<>();
        for (Reaction reaction : result.getReactions()) {
            reactions.add(reactionRepository.save(reaction));
        }
        result.setReactions(reactions);

        resultRepository.save(result);
    }

    @Transactional(readOnly = true)
    public List<TestResult> list() {
        return resultRepository.findAll();
    }

    @Transactional(readOnly = true)
    public int getCount(Long participantId, String testType, String info) {
        Optional<Participant> participant = participantRepository.findById(participantId);
        if (participant.isPresent()) {
            return resultRepository.findByParticipantAndTestTypeAndInfo(participant.get(), testType, info).size();
        }
        else {
            return 0;
        }
    }
}
