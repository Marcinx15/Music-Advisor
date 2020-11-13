package advisor;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface RequestHandler {

    HttpResponse<String> send(User user) throws IOException, InterruptedException;

    void handleResponse (HttpResponse<String> response);
}
