package advisor.controllers;

import advisor.User;
import advisor.models.FeaturedPlaylistsModel;
import advisor.views.FeaturedPlaylistsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpResponse;


public class FeaturedPlaylistsController extends Controller {

    private static final String URL = "/v1/browse/featured-playlists";

    private final FeaturedPlaylistsModel model;
    private final FeaturedPlaylistsView view;

    public FeaturedPlaylistsController(FeaturedPlaylistsModel model, FeaturedPlaylistsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
        return sendRequest(user, URL);
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
        view.getPagination().calculateNumberOfPages(model.getNumberOfElements());
    }

    @Override
    public String[] navigateSection() {
        view.printNextPage(model.getPlaylists());

        while (true) {
            String[] inputParts = Controller.readUserInput();
            String command = inputParts[0];

            switch (command) {
                case "next":
                    view.printNextPage(model.getPlaylists());
                    break;
                case "prev":
                    view.printPrevPage(model.getPlaylists());
                    break;
                default:
                    return inputParts;
            }

        }
    }
}
