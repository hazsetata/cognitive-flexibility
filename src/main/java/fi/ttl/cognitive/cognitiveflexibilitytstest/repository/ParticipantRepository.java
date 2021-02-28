package fi.ttl.cognitive.cognitiveflexibilitytstest.repository;

import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUsernameAndContextPath(String username, String contextPath);
    Participant findByUsername(String username);
    Participant findByUsernameAndPassword(String username, String password);
}
