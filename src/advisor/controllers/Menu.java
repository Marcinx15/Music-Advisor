package advisor.controllers;

import advisor.SpotifyConnection;
import advisor.SpotifyUtils;
import advisor.User;
import advisor.models.CategoriesModel;
import advisor.models.FeaturedPlaylistsModel;
import advisor.models.NewSongsModel;
import advisor.models.PlaylistsModel;
import advisor.views.CategoriesView;
import advisor.views.FeaturedPlaylistsView;
import advisor.views.NewSongsView;
import advisor.views.PlaylistsView;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    public static void mainMenu(String[] args) {
        Scanner in = new Scanner(System.in);
        User user = new User();
        SpotifyConnection connection = new SpotifyConnection(user);

        while (true) {
            String userInput = in.nextLine();
            String[] inputParts = userInput.split(" ");
            String command = inputParts[0];

            switch (command) {
                case "auth":
                    authorization(user, args);
                    break;
                case "new":
                    showSection(connection, new NewSongsController(new NewSongsModel(), new NewSongsView()));
                    break;
                case "featured":
                    showSection(connection,
                            new FeaturedPlaylistsController(new FeaturedPlaylistsModel(), new FeaturedPlaylistsView()));
                    break;
                case "categories":
                    showSection(connection, new CategoriesController(new CategoriesModel(), new CategoriesView()));
                    break;
                case "playlists":
                    String category = Arrays.stream(inputParts).skip(1).collect(Collectors.joining(" "));
                    showSection(connection, new PlaylistsController(new PlaylistsModel(category), new PlaylistsView()));
                    break;
                case "exit":
                    return;
            }
        }
    }

    public static void showSection (SpotifyConnection connection, Controller controller) {
        if (connection.getUser().isAuthorizedToSpotify()) {
            connection.getUserSpecificData(controller);
            controller.updateView();
        } else {
            System.out.println(noAccessMessage());
        }
    }

    public static void authorization(User user, String[] args) {
        for (int i = 0; i < args.length; i++){
            if ("-access".equals(args[i])) {
                SpotifyUtils.setSpotifyAccessServerPoint(args[i+1]);
            } else if ("-resource".equals(args[i])) {
                SpotifyUtils.setSpotifyApiURL(args[i+1]);
            }
        }

        String response = user.requestAccessToken(user.authorize());
        user.setSpotifyAccessToken(SpotifyUtils.getAccessTokenFromResponse(response));
        if (user.isAuthorizedToSpotify()) {
            System.out.println("Success!");
        }
    }


    public static String noAccessMessage() {
        return "Please, provide access for application.";
    }

}
