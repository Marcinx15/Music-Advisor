package advisor;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Base64;

final public class SpotifyUtils {
    public static String AUTHORIZATION_URL = "https://accounts.spotify.com";
    public static String API_URL = "https://api.spotify.com";
    public static final String CLIENT_ID = "b2627d0489ab45dfb279b77ef0124f29";
    public static final String SECRET_KEY = "3de5253366f84bac82bbd47e2eb4047b";

    public static String getUserAuthorizationURI(LocalHttpServer server){
        return AUTHORIZATION_URL + "/authorize" +
                "?client_id=" +
                CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" +
                server.getURI();
    }

    public static boolean isCodeInRequest(String requestParameters) {
        return requestParameters.startsWith("code");
    }

    public static boolean isErrorMessage(String message) {
        return message.startsWith("error");
    }

    public static String getCodeFromRequest(String requestParameters) {
        return requestParameters.substring(5);
    }

    public static String encodeClientIdAndSecretKey() {
        return Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes());
    }

    public static void setSpotifyAccessServerPoint(String accessServerPoint) {
        AUTHORIZATION_URL = accessServerPoint;
    }

    public static void setSpotifyApiURL(String apiUrl) {
        API_URL = apiUrl;
    }

    public static String getAccessTokenFromResponse(String response) {
        String accessTokenWithQuotes = response.split(",")[0].split(":")[1];
        return accessTokenWithQuotes.substring(1,accessTokenWithQuotes.length() - 1);
    }

    public static HttpRequest requestBuilder (User user, String endpoint) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + user.getSpotifyAccessToken())
                .uri(URI.create(API_URL + endpoint))
                .GET()
                .build();
    }

}
