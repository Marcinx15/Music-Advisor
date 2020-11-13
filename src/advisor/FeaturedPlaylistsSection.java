package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class FeaturedPlaylistsSection extends Section {
    private final Map<String, String> playlists = new LinkedHashMap<>();

    @Override
    public HttpResponse<String> send(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = SpotifyUtils.requestBuilder(user, "/v1/browse/featured-playlists");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void handleResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray jsonPlaylists = jsonObject.getAsJsonObject("playlists").getAsJsonArray("items");
        savePlaylists(jsonPlaylists);
    }

    private void savePlaylists(JsonArray jsonPlaylists) {
        jsonPlaylists.forEach(playlist -> playlists.put(playlist.getAsJsonObject().get("name").getAsString(),
                    playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString()));
    }

    @Override
    public void printSection() {
        playlists.forEach((name, url) -> {
            System.out.println(name);
            System.out.println(url);
            System.out.println();
        });
    }
}
