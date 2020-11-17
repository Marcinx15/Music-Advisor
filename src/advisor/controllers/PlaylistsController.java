package advisor.controllers;

import advisor.SpotifyUtils;
import advisor.User;
import advisor.models.CategoriesModel;
import advisor.models.PlaylistsModel;
import advisor.views.CategoriesView;
import advisor.views.PlaylistsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class PlaylistsController implements Controller {
    private final PlaylistsModel model;
    private final PlaylistsView view;

    public PlaylistsController(PlaylistsModel model, PlaylistsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
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
        CategoriesController categoriesController = new CategoriesController(new CategoriesModel(), new CategoriesView());
        categoriesController.handleResponse(categoriesController.sendRequest(user));
        return categoriesController.getCategoryId(model.getCategoryType());
    }

    private void savePlaylists(JsonArray jsonPlaylists) {
        jsonPlaylists.forEach(playlist -> model.addPlaylist(playlist.getAsJsonObject().get("name").getAsString(),
                playlist.getAsJsonObject().getAsJsonObject("external_urls").get("spotify").getAsString()));
    }

    public void updateView() {
        view.printPlaylists(model.getPlaylistsUrl());
    }

}
