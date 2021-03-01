package fi.ttl.cognitive.cognitiveflexibilitytstest.config;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.properties.ClientIdConfigurationProperties;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.ClientIdGuardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ClientIdConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ClientIdConfiguration.class);

    @Bean
    public ClientIdGuardService guardService(ClientIdConfigurationProperties configurationProperties) {
        if (StringUtils.hasText(configurationProperties.getClientId())) {
            return new ClientIdGuardService(configurationProperties.getClientId().trim());
        }
        else {
            log.warn("Required client-id for login is not specified (or empty text), client-id will not be necessary for succesful login.");

            return new ClientIdGuardService(null);
        }
    }
}
