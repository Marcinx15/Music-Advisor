package advisor.views;

import advisor.Pagination;
import java.util.Map;

public class FeaturedPlaylistsView {
    private final Pagination pagination;

    public FeaturedPlaylistsView(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    private void printPage(Map<String, String> playlists) {
        playlists.entrySet()
                .stream()
                .skip((pagination.getCurrentPage() - 1) * Pagination.ENTRIES_PER_PAGE)
                .limit(Pagination.ENTRIES_PER_PAGE)
                .forEach(entry -> {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue());
                    System.out.println();
                });
    }

    public void printNextPage(Map<String, String> playlists) {
        if (!pagination.nextPage()) {
            System.out.println("No more pages.");
            return;
        }

        printPage(playlists);
        pagination.printPageNumber();
    }

    public void printPrevPage(Map<String, String> playlists) {
        if (!pagination.prevPage()) {
            System.out.println("No more pages.");
            return;
        }

        printPage(playlists);
        pagination.printPageNumber();
    }
}
