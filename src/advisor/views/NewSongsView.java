package advisor.views;

import advisor.models.Song;
import java.util.Map;

public class NewSongsView {

    public void printNewSongs(Map<Song, String> songs) {
        songs.forEach((song, url) ->{
            System.out.println(song.getTitle());
            System.out.println(song.getArtists());
            System.out.println(url);
            System.out.println();
        });
    }
}
