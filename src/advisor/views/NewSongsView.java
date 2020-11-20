package advisor.views;

import advisor.Pagination;
import advisor.Song;
import java.util.Map;

public class NewSongsView {
    private final Pagination pagination;

    public NewSongsView(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    private void printPage(Map<Song, String> newSongs) {
        newSongs.entrySet()
                .stream()
                .skip((pagination.getCurrentPage() - 1) * Pagination.ENTRIES_PER_PAGE)
                .limit(Pagination.ENTRIES_PER_PAGE)
                .forEach(entry -> {
                    System.out.println(entry.getKey().getTitle());
                    System.out.println(entry.getKey().getArtists());
                    System.out.println(entry.getValue());
                    System.out.println();
                });
    }

    public void printNextPage(Map<Song, String> newSongs) {
        if (!pagination.nextPage()) {
            System.out.println("No more pages.");
            return;
        }

        printPage(newSongs);
        pagination.printPageNumber();
    }

    public void printPrevPage(Map<Song, String> newSongs) {
        if (!pagination.prevPage()) {
            System.out.println("No more pages.");
            return;
        }

        printPage(newSongs);
        pagination.printPageNumber();
    }


}
