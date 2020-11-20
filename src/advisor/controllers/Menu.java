package advisor.controllers;

import advisor.Pagination;
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
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    public static final Scanner in = new Scanner(System.in);

    public static void mainMenu(String[] args) {

        User user = new User();
        SpotifyConnection connection = new SpotifyConnection(user);
        processArguments(args);
        String userInput;
        String[] inputParts = null;

        while (true) {
            if (Objects.isNull(inputParts)) {
                userInput = in.nextLine();
                inputParts = userInput.split(" ");
            }

            String command = inputParts[0];
            Controller controller;


            switch (command) {
                case "auth":
                    authorization(user);
                    inputParts = null;
                    break;
                case "new":
                    controller = new NewSongsController(new NewSongsModel(), new NewSongsView(new Pagination()));
                    inputParts = showSection(connection, controller);
                    break;
                case "featured":
                    controller = new FeaturedPlaylistsController(
                            new FeaturedPlaylistsModel(), new FeaturedPlaylistsView(new Pagination()));
                    inputParts = showSection(connection, controller);
                    break;
                case "categories":
                    controller = new CategoriesController(new CategoriesModel(), new CategoriesView(new Pagination()));
                    inputParts = showSection(connection, controller);
                    break;
                case "playlists":
                    String category = Arrays.stream(inputParts).skip(1).collect(Collectors.joining(" "));
                    controller = new PlaylistsController(new PlaylistsModel(category), new PlaylistsView(new Pagination()));
                    inputParts = showSection(connection, controller);
                    break;
                case "exit":
                    return;
            }
        }
    }

    public static void processArguments(String[] args) {
        for (int i = 0; i < args.length; i++){
            if ("-access".equals(args[i])) {
                SpotifyUtils.setSpotifyAccessServerPoint(args[i+1]);
            } else if ("-resource".equals(args[i])) {
                SpotifyUtils.setSpotifyApiURL(args[i+1]);
            } else if ("-page".equals(args[i])) {
                Pagination.ENTRIES_PER_PAGE = Integer.parseInt(args[i+1]);
            }
        }
    }

    public static String[] showSection (SpotifyConnection connection, Controller controller) {

        if (connection.getUser().isAuthorizedToSpotify()) {
            connection.getUserSpecificData(controller);
            return controller.navigateSection();
        } else {
            System.out.println(noAccessMessage());
            return null;
        }
    }

    public static void authorization(User user) {
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
