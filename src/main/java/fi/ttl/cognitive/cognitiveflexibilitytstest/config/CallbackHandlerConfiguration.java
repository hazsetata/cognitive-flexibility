package fi.ttl.cognitive.cognitiveflexibilitytstest.config;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.properties.CallbackHandlerConfigurationProperties;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.CallbackHandlerService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CallbackHandlerConfigurationProperties.class)
public class CallbackHandlerConfiguration {
    @Bean
    public CallbackHandlerService callbackHandlerService(CallbackHandlerConfigurationProperties configurationProperties) {
        return new CallbackHandlerService(
                configurationProperties.getCallbackUrl(),
                configurationProperties.getPayloadTemplate(),
                configurationProperties.isLogHttpRequest()
        );
    }
}
