package advisor;

import advisor.controllers.RequestHandler;

import java.io.IOException;

public class SpotifyConnection {

    final private User user;

    public SpotifyConnection(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void getUserSpecificData(RequestHandler handler) {
        try {
            handler.handleResponse(handler.sendRequest(user));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}
