package advisor.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlaylistsModel {
    private final String categoryType;
    private final Map<String, String> playlistsUrl = new LinkedHashMap<>();

    public PlaylistsModel(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public Map<String, String> getPlaylistsUrl() {
        return playlistsUrl;
    }

    public void addPlaylist(String playlistName, String playlistUrl) {
        playlistsUrl.put(playlistName, playlistUrl);
    }

}
