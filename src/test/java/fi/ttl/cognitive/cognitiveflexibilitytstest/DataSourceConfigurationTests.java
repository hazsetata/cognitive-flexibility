package fi.ttl.cognitive.cognitiveflexibilitytstest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.DataSourceConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataSourceConfigurationTests {
    @Test
    public void databaseUrlDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withSystemProperties("DATABASE_URL=mysql://cognitiveuser:changeme@localhost:3306/cognitivedb")
                .withConfiguration(AutoConfigurations.of(DataSourceConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());
        });
    }

    @Test
    public void herokuMariDbUrlDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withSystemProperties("JAWSDB_MARIA_URL=mysql://cognitiveuser:changeme@localhost:3306/cognitivedb")
                .withConfiguration(AutoConfigurations.of(DataSourceConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());
        });
    }

    @Test
    public void herokuMySqlUrlDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withSystemProperties("JAWSDB_URL=mysql://cognitiveuser:changeme@localhost:3306/cognitivedb")
                .withConfiguration(AutoConfigurations.of(DataSourceConfiguration.class));
        contextRunner.run((context) -> {
            assertNull(context.getStartupFailure());
        });
    }

    @Test
    public void databaseUrlNotDefined() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.DEBUG))
                .withConfiguration(AutoConfigurations.of(DataSourceConfiguration.class));
        contextRunner.run((context) -> {
            assertNotNull(context.getStartupFailure());
            assertTrue(context.getStartupFailure().getMessage().contains("Error creating bean with name 'dataSource'"));
            assertTrue(extractCauseMessages(context).contains("Database configuration URL not specified."));
        });
    }

    private List<String> extractCauseMessages(AssertableApplicationContext context) {
        List<String> retValue = new ArrayList<>();
        Throwable currentCause = context.getStartupFailure().getCause();

        while (currentCause != null) {
            retValue.add(currentCause.getMessage());
            currentCause = currentCause.getCause();
        }

        return retValue;
    }
}
