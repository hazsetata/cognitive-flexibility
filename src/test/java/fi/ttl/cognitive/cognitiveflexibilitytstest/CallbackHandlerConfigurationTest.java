package fi.ttl.cognitive.cognitiveflexibilitytstest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.CallbackHandlerConfiguration;
import fi.ttl.cognitive.cognitiveflexibilitytstest.service.CallbackHandlerService;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CallbackHandlerConfigurationTest {
    @Test
    public void callbackNotConfigured_full() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withConfiguration(AutoConfigurations.of(CallbackHandlerConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());

            CallbackHandlerService callbackHandlerService = context.getBean(CallbackHandlerService.class);
            assertNotNull(callbackHandlerService);

            assertFalse(callbackHandlerService.notifyCallbackForSessionToken("token1234"));
        });
    }

    @Test
    public void callbackNotConfigured_urlOnly() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withPropertyValues(
                        "ttl.cognitive.callback.callbackUrl=http://localhost:7777/callbackPath",
                        "ttl.cognitive.callback.payloadTemplate="
                )
                .withConfiguration(AutoConfigurations.of(CallbackHandlerConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());

            CallbackHandlerService callbackHandlerService = context.getBean(CallbackHandlerService.class);
            assertNotNull(callbackHandlerService);

            assertFalse(callbackHandlerService.notifyCallbackForSessionToken("token1234"));
        });
    }

    @Test
    public void callbackNotConfigured_wrongTemplate() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withPropertyValues(
                        "ttl.cognitive.callback.callbackUrl=http://localhost:7777/callbackPath",
                        "ttl.cognitive.callback.payloadTemplate={\"session\": \"{{callbackTokenWrong}}\"}"
                )
                .withConfiguration(AutoConfigurations.of(CallbackHandlerConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());

            CallbackHandlerService callbackHandlerService = context.getBean(CallbackHandlerService.class);
            assertNotNull(callbackHandlerService);

            assertFalse(callbackHandlerService.notifyCallbackForSessionToken("token1234"));
        });
    }

    @Test
    public void callbackConfigured_defaultPayload() throws IOException {
        try (MockWebServer mockServer = new MockWebServer()) {
            mockServer.enqueue(new MockResponse());
            mockServer.start();

            HttpUrl callbackUrl = mockServer.url("/callbackPath");

            ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                    .withInitializer(new ConfigDataApplicationContextInitializer())
                    .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                    .withPropertyValues(
                            "ttl.cognitive.callback.callbackUrl=" + callbackUrl.toString()
                    )
                    .withConfiguration(AutoConfigurations.of(CallbackHandlerConfiguration.class));
            contextRunner.run((context) -> {
                assertNull(context.getStartupFailure());

                CallbackHandlerService callbackHandlerService = context.getBean(CallbackHandlerService.class);
                assertNotNull(callbackHandlerService);

                assertTrue(callbackHandlerService.notifyCallbackForSessionToken("token1234"));

                RecordedRequest request = mockServer.takeRequest();
                assertEquals("/callbackPath", request.getPath());
                assertEquals("POST", request.getMethod());
                assertEquals("{\"session\": \"token1234\"}", request.getBody().readUtf8());
            });
        }
    }

    @Test
    public void callbackConfigured_customPayload() throws IOException {
        try (MockWebServer mockServer = new MockWebServer()) {
            mockServer.enqueue(new MockResponse());
            mockServer.start();

            HttpUrl callbackUrl = mockServer.url("/callbackPath");

            ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                    .withInitializer(new ConfigDataApplicationContextInitializer())
                    .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                    .withPropertyValues(
                            "ttl.cognitive.callback.callbackUrl=" + callbackUrl.toString(),
                            "ttl.cognitive.callback.payloadTemplate={\"thing\": {\"sub\" : \"{{callbackToken}}\"}}"
                    )
                    .withConfiguration(AutoConfigurations.of(CallbackHandlerConfiguration.class));
            contextRunner.run((context) -> {
                assertNull(context.getStartupFailure());

                CallbackHandlerService callbackHandlerService = context.getBean(CallbackHandlerService.class);
                assertNotNull(callbackHandlerService);

                assertTrue(callbackHandlerService.notifyCallbackForSessionToken("token1234"));

                RecordedRequest request = mockServer.takeRequest();
                assertEquals("/callbackPath", request.getPath());
                assertEquals("POST", request.getMethod());
                assertEquals("{\"thing\": {\"sub\" : \"token1234\"}}", request.getBody().readUtf8());
            });
        }
    }
}