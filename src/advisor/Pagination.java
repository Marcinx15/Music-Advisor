package advisor;

public class Pagination {
    public static int ENTRIES_PER_PAGE = 5;
    private int numberOfPages;
    private int currentPage;

    public Pagination() {
        this.currentPage = 0;
    }

    public void calculateNumberOfPages(int numberOfElements) {
        this.numberOfPages = (int) Math.ceil((double)numberOfElements / ENTRIES_PER_PAGE);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean nextPage() {
        if (currentPage == numberOfPages) {
            return false;
        }
        currentPage++;
        return true;
    }

    public boolean prevPage() {
        if (currentPage == 1) {
            return false;
        }
        currentPage--;
        return true;
    }


    public void printPageNumber() {
        System.out.println("---PAGE " + currentPage + " OF " + numberOfPages + "---");
    }
}
