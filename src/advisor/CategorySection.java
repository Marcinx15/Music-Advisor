package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class CategorySection extends Section {
    private final Map<String, String> categories = new LinkedHashMap<>();

    @Override
    public HttpResponse<String> send(User user) throws IOException, InterruptedException {
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
            categories.put(category.getAsJsonObject().get("name").getAsString(),
                    category.getAsJsonObject().get("id").getAsString())
        );
    }

    @Override
    public void printSection() {
        categories.keySet().forEach(System.out::println);
    }

    public String getIdForCategory(String name) {
        return categories.getOrDefault(name, "Unknown");
    }

}
