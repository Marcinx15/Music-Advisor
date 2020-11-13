package advisor;

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
                    showSection(connection, new NewSongsSection());
                    break;
                case "featured":
                    showSection(connection, new FeaturedPlaylistsSection());
                    break;
                case "categories":
                    showSection(connection, new CategorySection());
                    break;
                case "playlists":
                    String category = Arrays.stream(inputParts).skip(1).collect(Collectors.joining(" "));
                    showSection(connection, new PlaylistsSection(category));
                    break;
                case "exit":
                    return;
            }
        }
    }

    public static void showSection (SpotifyConnection connection, Section section) {
        if (connection.getUser().isAuthorizedToSpotify()) {
            connection.getUserSpecificData(section);
            section.printSection();
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
