package fi.ttl.cognitive.cognitiveflexibilitytstest.repository;

import fi.ttl.cognitive.cognitiveflexibilitytstest.domain.AuthenticationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationInformationRepository extends JpaRepository<AuthenticationInformation, Long> {
}
