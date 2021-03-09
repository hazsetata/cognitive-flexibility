package fi.ttl.cognitive.cognitiveflexibilitytstest.service;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class CallbackHandlerService {
    private static final String CALLBACK_TOKEN_PLACEHOLDER = "{{callbackToken}}";
    private static final MediaType JSON_PAYLOAD = MediaType.get("application/json; charset=utf-8");

    private static final Logger log = LoggerFactory.getLogger(CallbackHandlerService.class);

    private OkHttpClient client;
    private String callbackUrl;
    private String payloadTemplate;

    public CallbackHandlerService(
            String callbackUrl,
            String payloadTemplate,
            boolean logHttpRequest) {
        client = null;

        if (!StringUtils.hasText(callbackUrl)) {
            log.warn("Callback URL is not specified, callback-handling will be disabled.");
        }
        else if (!StringUtils.hasText(payloadTemplate)) {
            log.warn("Payload template is not specified, callback-handling will be disabled.");
        }
        else if (!payloadTemplate.contains(CALLBACK_TOKEN_PLACEHOLDER)){
            log.warn("Payload template doesn't contain placeholder for callback-token: " + CALLBACK_TOKEN_PLACEHOLDER);
            log.warn("Callback-handling will be disabled.");
        }
        else {
            log.info("Callback handler configuration OK, initializing instance.");

            if (!logHttpRequest) {
                this.client = new OkHttpClient();
            }
            else {
                log.info("Callback HTTP-request logging activated.");
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                this.client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            }

            this.callbackUrl = callbackUrl;
            this.payloadTemplate = payloadTemplate;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean notifyCallbackForSessionToken(String callbackToken) {
        if (client != null) {
            String payloadWithToken = payloadTemplate.replace(CALLBACK_TOKEN_PLACEHOLDER, callbackToken);

            RequestBody body = RequestBody.create(payloadWithToken, JSON_PAYLOAD);
            Request request = new Request.Builder()
                    .url(callbackUrl)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return true;
                }
                else {
                    log.warn("Callback executed, but server returned error.");
                    return false;
                }
            }
            catch (IOException e) {
                log.debug("Error executing callback.", e);
            }
        }

        return false;
    }

}
