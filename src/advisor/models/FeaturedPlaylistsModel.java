package advisor.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeaturedPlaylistsModel {
    private final Map<String, String> playlistsUrl = new LinkedHashMap<>();

    public Map<String, String> getPlaylists() {
        return playlistsUrl;
    }

    public void addPlaylist(String playlistName, String playlistUrl) {
        playlistsUrl.put(playlistName, playlistUrl);
    }

    public int getNumberOfElements() {
        return playlistsUrl.size();
    }

}
