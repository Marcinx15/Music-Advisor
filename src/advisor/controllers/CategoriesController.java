package advisor.controllers;

import advisor.SpotifyUtils;
import advisor.User;
import advisor.models.CategoriesModel;
import advisor.views.CategoriesView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CategoriesController implements Controller {
    private final CategoriesModel model;
    private final CategoriesView view;

    public CategoriesController(CategoriesModel model, CategoriesView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public HttpResponse<String> sendRequest(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = SpotifyUtils.requestBuilder(user, "/v1/browse/categories");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
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
    }

    public void updateView() {
        view.printCategoryNames(model.getCategories());
    }

    public String getCategoryId(String categoryName) {
        return model.getIdForCategory(categoryName);
    }


}
