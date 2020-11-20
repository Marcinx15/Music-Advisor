package advisor.controllers;

import advisor.User;
import advisor.models.CategoriesModel;
import advisor.views.CategoriesView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.http.HttpResponse;

public class CategoriesController extends Controller {

    private static final String URL = "/v1/browse/categories";

    private final CategoriesModel model;
    private final CategoriesView view;

    public CategoriesController(CategoriesModel model, CategoriesView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
        return sendRequest(user, URL);
    }

    @Override
    public void handleResponse(HttpResponse<String> response) {
        String responseBody = response.body();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray jsonCategories = jsonObject.getAsJsonObject("categories").getAsJsonArray("items");
        jsonCategories.forEach(category ->
            model.addCategory(category.getAsJsonObject().get("name").getAsString(),
                    category.getAsJsonObject().get("id").getAsString())
        );
        view.getPagination().calculateNumberOfPages(model.getNumberOfElements());
    }

    public String getCategoryId(String categoryName) {
        return model.getIdForCategory(categoryName);
    }

    public String[] navigateSection() {
        view.printNextPage(model.getCategories());

        while (true) {
            String[] inputParts = Controller.readUserInput();
            String command = inputParts[0];

            switch (command) {
                case "next":
                    view.printNextPage(model.getCategories());
                    break;
                case "prev":
                    view.printPrevPage(model.getCategories());
                    break;
                default:
                    return inputParts;
            }

        }
    }


}
