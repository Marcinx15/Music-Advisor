package advisor.controllers;

import advisor.Song;
import advisor.User;
import advisor.models.NewSongsModel;
import advisor.views.NewSongsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpResponse;



public class NewSongsController extends Controller {

    private static final String URL = "/v1/browse/new-releases";

    private final NewSongsModel model;
    private final NewSongsView view;

    public NewSongsController(NewSongsModel model, NewSongsView view) {
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
        JsonArray jsonAlbums = jsonObject.getAsJsonObject("albums").getAsJsonArray("items");
        addSongs(jsonAlbums);
    }

    private void addSongs (JsonArray albums) {
        albums.forEach(album ->{
            Song song = new Song(album.getAsJsonObject().get("name").getAsString());
            addArtistsToSong(song, album.getAsJsonObject().getAsJsonArray("artists"));
            model.addSong(song, album.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString());
        });
        view.getPagination().calculateNumberOfPages(model.getNumberOfElements());
    }

    private void addArtistsToSong(Song song, JsonArray artists){
        artists.forEach(artist -> song.addArtists(artist.getAsJsonObject().get("name").getAsString()));
    }

    @Override
    public String[] navigateSection() {
        view.printNextPage(model.getSongsUrl());

        while (true) {
            String[] inputParts = Controller.readUserInput();
            String command = inputParts[0];

            switch (command) {
                case "next":
                    view.printNextPage(model.getSongsUrl());
                    break;
                case "prev":
                    view.printPrevPage(model.getSongsUrl());
                    break;
                default:
                    return inputParts;
            }

        }
    }


}
