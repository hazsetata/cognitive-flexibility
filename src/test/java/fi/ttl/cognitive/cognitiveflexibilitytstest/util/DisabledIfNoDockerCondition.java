package fi.ttl.cognitive.cognitiveflexibilitytstest.util;

import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;

public class DisabledIfNoDockerCondition implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        DockerClientFactory factory = DockerClientFactory.instance();
        DockerClient client;
        try {
            client = factory.client();
        }
        catch (Exception e) {
            client = null;
        }

        if (client == null) {
            return ConditionEvaluationResult.disabled("Test disabled as no Docker client can be created.");
        }
        else {
            return ConditionEvaluationResult.enabled("Test enabled as Docker client was found.");
        }
    }
}
