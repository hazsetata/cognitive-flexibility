package fi.ttl.cognitive.cognitiveflexibilitytstest.repository;

import java.util.List;

import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.Participant;
import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findByParticipantAndTestTypeAndInfo(Participant participant, String testType, String info);
}
