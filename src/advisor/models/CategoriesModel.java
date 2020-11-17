package advisor.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class CategoriesModel {
    private final Map<String, String> categoriesId = new LinkedHashMap<>();


    public Map<String, String> getCategories() {
        return categoriesId;
    }

    public void addCategory(String categoryName, String categoryId) {
        categoriesId.put(categoryName, categoryId);
    }

    public String getIdForCategory(String name) {
        return categoriesId.getOrDefault(name, "Unknown");
    }

}
