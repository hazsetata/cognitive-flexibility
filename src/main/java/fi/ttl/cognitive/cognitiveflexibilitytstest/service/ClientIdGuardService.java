package fi.ttl.cognitive.cognitiveflexibilitytstest.service;

public class ClientIdGuardService {
    private final String clientId;

    public ClientIdGuardService(String clientId) {
        this.clientId = clientId;
    }

    public boolean requestAllowed(String requestClientId) {
        if (clientId != null) {
            return clientId.equals(requestClientId);
        }

        return true;
    }
}
