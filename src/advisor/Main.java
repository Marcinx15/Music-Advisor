package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine().toLowerCase();
            String[] inputParts = userInput.split(" ");
            String command = inputParts[0];

            switch (command) {
                case "new":
                    showNewReleases();
                    break;
                case "featured":
                    showFeatured();
                    break;
                case "categories":
                    showCategories();
                    break;
                case "playlists":
                    showPlaylists(inputParts[1]);
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
            }
        }
    }

    public static void showNewReleases(){
        System.out.println("---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    }

    public static void showFeatured(){
        System.out.println("---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    public static void showCategories(){
        System.out.println("---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    public static void showPlaylists(String category) {
        System.out.println("---" + category.toUpperCase() + " PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll");
    }
}
