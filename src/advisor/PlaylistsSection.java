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

public class PlaylistsSection extends Section {
    private final String categoryType;
    private final Map<String, String> nameToURL = new LinkedHashMap<>();

    public PlaylistsSection(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public HttpResponse<String> send(User user) throws IOException, InterruptedException {
        String categoryID = getCurrentPlaylistID(user);
        if  ("Unknown".equals(categoryID)){
            System.out.println("Unknown category name.");
            return null;
        }
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request =
                SpotifyUtils.requestBuilder(user, "/v1/browse/categories/" + categoryID + "/playlists");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void handleResponse(HttpResponse<String> response) {
        if (response == null) {
            return;
        }

        String responseBody = response.body();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

        if (jsonObject.get("error") != null){
            JsonObject errorObject = jsonObject.get("error").getAsJsonObject();
            System.out.println(errorObject.get("message").getAsString());
            return;
        }

        savePlaylists(jsonObject.getAsJsonObject("playlists").getAsJsonArray("items"));
    }

    private String getCurrentPlaylistID(User user) throws IOException, InterruptedException {
        CategorySection categorySection = new CategorySection();
        categorySection.handleResponse(categorySection.send(user));
        return categorySection.getIdForCategory(categoryType);
    }

    private void savePlaylists(JsonArray jsonPlaylists) {
        jsonPlaylists.forEach(playlist -> nameToURL.put(playlist.getAsJsonObject().get("name").getAsString(),
                playlist.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString()));
    }

    @Override
    public void printSection() {
        nameToURL.forEach((k,v) -> {
            System.out.println(k);
            System.out.println(v);
            System.out.println();
        });
    }

}
