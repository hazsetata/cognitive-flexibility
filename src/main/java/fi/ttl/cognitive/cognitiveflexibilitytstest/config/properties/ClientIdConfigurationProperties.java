package fi.ttl.cognitive.cognitiveflexibilitytstest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ttl.cognitive.client")
public class ClientIdConfigurationProperties {
    /**
     * If set, the same clientId must be specified on login requests, otherwise
     * the login will be rejected.
     */
    private String clientId = null;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
