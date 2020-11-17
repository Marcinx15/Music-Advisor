package advisor.controllers;

import advisor.SpotifyUtils;
import advisor.User;
import advisor.models.FeaturedPlaylistsModel;
import advisor.views.FeaturedPlaylistsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class FeaturedPlaylistsController implements Controller {
    private final FeaturedPlaylistsModel model;
    private final FeaturedPlaylistsView view;

    public FeaturedPlaylistsController(FeaturedPlaylistsModel model, FeaturedPlaylistsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
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
        jsonPlaylists.forEach(playlist -> model.addPlaylist(playlist.getAsJsonObject().get("name").getAsString(),
                    playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString()));
    }

    public void updateView() {
        view.printFeaturedPlaylists(model.getPlaylists());
    }

}
