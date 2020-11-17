package advisor.views;

import java.util.Map;

public class FeaturedPlaylistsView {

    public void printFeaturedPlaylists(Map<String, String> playlists) {
        playlists.forEach((name, url) -> {
            System.out.println(name);
            System.out.println(url);
            System.out.println();
        });
    }
}
