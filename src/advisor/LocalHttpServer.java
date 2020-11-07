package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LocalHttpServer {
    final private String URI;
    final private HttpServer httpServer;

    public LocalHttpServer(String URI){
        this.URI = URI;
        this.httpServer = createServer();
    }

    private HttpServer createServer() {
        HttpServer server = null;
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(getPort()), 0);
        } catch (IOException exception) {
            exception.printStackTrace();

        }
        return server;
    }

    public void createMainContext(User user) {
        httpServer.createContext("/",
            httpExchange -> {
                String requestQuery = httpExchange.getRequestURI().getQuery();
                String response = "";

                if (requestQuery == null || SpotifyUtils.isErrorInRequest(requestQuery)) {
                    response = "Authorization code not found. Try again.";
                } else if (SpotifyUtils.isCodeInRequest(requestQuery)) {
                    response = "Got the code. Return back to your program.";
                    user.setSpotifyCode(SpotifyUtils.getCodeFromRequest(requestQuery));
                    user.setAuthorizedToSpotify(true);
                }

                httpExchange.sendResponseHeaders(200, response.length());
                httpExchange.getResponseBody().write(response.getBytes());
                httpExchange.getResponseBody().close();
            });
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

    public String getURI() {
        return URI;
    }

    private int getPort() {
        return Integer.parseInt(URI.substring(URI.lastIndexOf(":")+ 1));
    }
}
