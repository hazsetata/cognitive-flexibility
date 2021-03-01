package fi.ttl.cognitive.cognitiveflexibilitytstest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ttl.cognitive.callback")
public class CallbackHandlerConfigurationProperties {
    /**
     * The URL to POST the HTTP request to, to indicate that a user finished the given cognitive-test session..
     */
    private String callbackUrl = "";

    /**
     * The POST payload that will be POSTed to the callback-url. Handled simply as a string, and its
     * '{{callbackToken}}' part will be replaced with the token that was received on user login.
     */
    private String payloadTemplate = "{\"session\": \"{{callbackToken}}\"}";

    /**
     * Controls if the callback request will be logged or not.
     */
    private boolean logHttpRequest = false;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPayloadTemplate() {
        return payloadTemplate;
    }

    public void setPayloadTemplate(String payloadTemplate) {
        this.payloadTemplate = payloadTemplate;
    }

    public boolean isLogHttpRequest() {
        return logHttpRequest;
    }

    public void setLogHttpRequest(boolean logHttpRequest) {
        this.logHttpRequest = logHttpRequest;
    }
}
