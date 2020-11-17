package advisor.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewSongsModel {
    private final Map<Song, String> songsUrl = new LinkedHashMap<>();

    public Map<Song, String> getSongsUrl() {
        return songsUrl;
    }

    public void addSong(Song song, String songUrl) {
        songsUrl.put(song, songUrl);
    }
}
