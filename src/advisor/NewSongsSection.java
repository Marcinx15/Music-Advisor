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

public class NewSongsSection extends Section {
    private final Map<Song, String> songs = new LinkedHashMap<>();

    @Override
    public HttpResponse<String> send(User user) throws IOException, InterruptedException {
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
            songs.put(song, album.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString());
        });
    }

    private void addArtistsToSong(Song song, JsonArray artists){
        artists.forEach(artist -> song.addArtists(artist.getAsJsonObject().get("name").getAsString()));
    }

    @Override
    public void printSection() {
        songs.forEach((song, url) ->{
            System.out.println(song.getTitle());
            System.out.println(song.getArtists());
            System.out.println(url);
            System.out.println();
        });
    }

}
