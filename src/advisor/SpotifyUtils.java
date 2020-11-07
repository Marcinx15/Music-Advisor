package advisor;

import java.util.Base64;

final public class SpotifyUtils {
    public static String URL = "https://accounts.spotify.com";
    public static final String CLIENT_ID = "b2627d0489ab45dfb279b77ef0124f29";
    public static final String SECRET_KEY = "3de5253366f84bac82bbd47e2eb4047b";

    public static String getUserAuthorizationURI(LocalHttpServer server){
        return URL + "/authorize" +
                "?client_id=" +
                CLIENT_ID +
                "&response_type=code" +
                "&redirect_uri=" +
                server.getURI();
    }

    public static boolean isCodeInRequest(String requestParameters) {
        return requestParameters.startsWith("code");
    }

    public static boolean isErrorInRequest(String requestParameters) {
        return requestParameters.startsWith("error");
    }

    public static String getCodeFromRequest(String requestParameters) {
        return requestParameters.substring(5);
    }

    public static String encodeClientIdAndSecretKey() {
        return Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes());
    }

    public static void setSpotifyAccessServerPoint(String accessServerPoint) {
        URL = accessServerPoint;
    }
}
