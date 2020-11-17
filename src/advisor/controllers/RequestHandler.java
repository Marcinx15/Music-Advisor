package advisor.controllers;

import advisor.User;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface RequestHandler {

    HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException;

    void handleResponse (HttpResponse<String> response);
}
