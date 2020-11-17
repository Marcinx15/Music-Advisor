package advisor.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Song {
    private final String title;
    private final List<String> artists = new ArrayList<>();


    public Song(String title) {
        this.title = title;
    }

    public void addArtists(String... artists) {
        this.artists.addAll(Arrays.asList(artists));
    }


    public String getTitle() {
        return title;
    }

    public List<String> getArtists() {
        return artists;
    }


}
