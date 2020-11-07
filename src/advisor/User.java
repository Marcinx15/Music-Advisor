package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class User {
    private boolean authorizedToSpotify;
    private String spotifyCode;

    public User() {
        authorizedToSpotify = false;
    }

    public boolean isAuthorizedToSpotify() {
        return authorizedToSpotify;
    }

    public void setAuthorizedToSpotify(boolean authorizedToSpotify) {
        this.authorizedToSpotify = authorizedToSpotify;
    }

    public LocalHttpServer authorize() {
        LocalHttpServer server = new LocalHttpServer("http://localhost:8080");
        server.createMainContext(this);
        server.start();

        System.out.println("use this link to request the access code:");
        System.out.println(SpotifyUtils.getUserAuthorizationURI(server));
        System.out.println("waiting for code...");

        while (!isAuthorizedToSpotify()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        server.stop();
        System.out.println("code received");
        return server;
    }

    public String getAccessToken(LocalHttpServer server) {
        System.out.println("making http request for access_token...");

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + SpotifyUtils.encodeClientIdAndSecretKey())
                .uri(URI.create(SpotifyUtils.URL + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code="
                        + spotifyCode + "&redirect_uri=" + server.getURI()))
                .build();

        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void setSpotifyCode(String spotifyCode) {
        this.spotifyCode = spotifyCode;
    }
}
