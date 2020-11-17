package advisor.views;

import java.util.Map;

public class CategoriesView {

    public void printCategoryNames(Map<String, String> categoriesId) {
        categoriesId.keySet().forEach(System.out::println);
    }
}
