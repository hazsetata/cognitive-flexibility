package fi.ttl.cognitive.cognitiveflexibilitytstest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.ClientIdConfiguration;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.ClientIdGuardService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

class ClientIdConfigurationTest {
    @Test
    public void clientIdDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withPropertyValues("ttl.cognitive.client.clientId=testClientId")
                .withConfiguration(AutoConfigurations.of(ClientIdConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());

            ClientIdGuardService guardService = context.getBean(ClientIdGuardService.class);
            assertNotNull(guardService);

            assertFalse(guardService.requestAllowed(null));
            assertFalse(guardService.requestAllowed(""));
            assertFalse(guardService.requestAllowed(" "));
            assertFalse(guardService.requestAllowed("abrakadabra"));
            assertTrue(guardService.requestAllowed("testClientId"));
        });
    }

    @Test
    public void clientIdNotDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withConfiguration(AutoConfigurations.of(ClientIdConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());

            ClientIdGuardService guardService = context.getBean(ClientIdGuardService.class);
            assertNotNull(guardService);

            assertTrue(guardService.requestAllowed(null));
            assertTrue(guardService.requestAllowed(""));
            assertTrue(guardService.requestAllowed(" "));
            assertTrue(guardService.requestAllowed("abrakadabra"));
            assertTrue(guardService.requestAllowed("testClientId"));
        });
    }
}