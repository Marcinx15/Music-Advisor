package advisor.controllers;


import advisor.SpotifyUtils;
import advisor.User;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public abstract class Controller implements RequestHandler {

    abstract String[] navigateSection();

    static String[] readUserInput() {
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        return userInput.split(" ");
    }

    HttpResponse<String> sendRequest(User user, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = SpotifyUtils.requestBuilder(user, url);
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
