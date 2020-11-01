package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        Scanner in = new Scanner(System.in);
        User user = new User();
        while (true) {
            String userInput = in.nextLine().toLowerCase();
            String[] inputParts = userInput.split(" ");
            String command = inputParts[0];


            switch (command) {
                case "auth":
                    authorize(user);
                    System.out.println("---SUCCESS---");
                    break;
                case "new":
                    showNewReleases(user);
                    break;
                case "featured":
                    showFeatured(user);
                    break;
                case "categories":
                    showCategories(user);
                    break;
                case "playlists":
                    showPlaylists(inputParts[1], user);
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
            }
        }
    }



    public static void showNewReleases(User user){
        if (user.isAuthorizedToSpotify()) {
            System.out.println("---NEW RELEASES---\n" +
                    "Mountains [Sia, Diplo, Labrinth]\n" +
                    "Runaway [Lil Peep]\n" +
                    "The Greatest Show [Panic! At The Disco]\n" +
                    "All Out Life [Slipknot]");
        } else {
            System.out.println(noAccessMessage());
        }
    }

    public static void showFeatured(User user){
        if (user.isAuthorizedToSpotify()) {
            System.out.println("---FEATURED---\n" +
                    "Mellow Morning\n" +
                    "Wake Up and Smell the Coffee\n" +
                    "Monday Motivation\n" +
                    "Songs to Sing in the Shower");
        } else {
            System.out.println(noAccessMessage());
        }
    }

    public static void showCategories(User user){
        if (user.isAuthorizedToSpotify()) {
            System.out.println("---CATEGORIES---\n" +
                    "Top Lists\n" +
                    "Pop\n" +
                    "Mood\n" +
                    "Latin");
        } else {
            System.out.println(noAccessMessage());
        }
    }

    public static void showPlaylists(String category, User user) {
        if (user.isAuthorizedToSpotify()) {
            System.out.println("---" + category.toUpperCase() + " PLAYLISTS---\n" +
                    "Walk Like A Badass  \n" +
                    "Rage Beats  \n" +
                    "Arab Mood Booster  \n" +
                    "Sunday Stroll");
        } else {
            System.out.println(noAccessMessage());
        }
    }

    public static String noAccessMessage() {
        return "Please, provide access for application.";
    }

    public static void authorize(User user) {
        StringBuilder sb = new StringBuilder("https://accounts.spotify.com/authorize?");
        sb.append("client_id=b2627d0489ab45dfb279b77ef0124f29&");
        sb.append("redirect_uri=http://localhost:8080&response_type=code");
        user.setAuthorizedToSpotify(true);
        System.out.println(sb.toString());
    }
}
