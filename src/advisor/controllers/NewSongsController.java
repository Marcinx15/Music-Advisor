package advisor.controllers;

import advisor.models.Song;
import advisor.SpotifyUtils;
import advisor.User;
import advisor.models.NewSongsModel;
import advisor.views.NewSongsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class NewSongsController implements Controller{
    private final NewSongsModel model;
    private final NewSongsView view;

    public NewSongsController(NewSongsModel model, NewSongsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = SpotifyUtils.requestBuilder(user, "/v1/browse/new-releases");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
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
    }

    private void addArtistsToSong(Song song, JsonArray artists){
        artists.forEach(artist -> song.addArtists(artist.getAsJsonObject().get("name").getAsString()));
    }

    public void updateView() {
        view.printNewSongs(model.getSongsUrl());
    }

}
