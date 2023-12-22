package communityapp;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "src/communityapp/community.txt";

    public static void main(String[] args) {
        Community community = Community.getInstance();
        RechargeZoneObserver observer = new RechargeZoneObserver(community);

        while (true) {
            clearConsole();

            System.out.println("Bienvenue dans le programme de configuration de la Communauté d'Agglomération.");
            System.out.println("1) Configurer la communauté");
            System.out.println("2) Sauvegarder la communauté dans un fichier");
            System.out.println("3) Charger la communauté à partir d'un fichier");
            System.out.println("4) Quitter");
            System.out.print("Votre choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the empty line

            switch (choice) {
                case 1:
                    configureCommunity(community);
                    break;
                case 2:
                    saveCommunityToFile(community);
                    break;
                case 3:
                    loadCommunityFromFileAndUpdate(community);
                    break;
                case 4:
                    System.out.println("Fin du programme");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void configureCommunity(Community community) {
        clearConsole();

        System.out.println("\nBienvenue dans le menu de configuration de la Communauté d'Agglomération.");

        // Configure the number of cities and their names
        System.out.print("Combien de villes dans la CA ? ");
        int numCities = scanner.nextInt();
        scanner.nextLine(); // Consume the empty line

        for (int i = 0; i < numCities; i++) {
            System.out.print("Nom de la ville " + (char) ('A' + i) + ": ");
            String cityName = scanner.nextLine();
            community.addCity(cityName);
        }

        // Continue with the configuration menu
        configureCommunityMenu(community);
    }

    private static void configureCommunityMenu(Community community) {
        while (true) {
            clearConsole();

            System.out.println("\nMenu :");
            System.out.println("1) Ajouter une route");
            System.out.println("2) Ajouter/Retirer une zone de recharge");
            System.out.println("3) Terminer la configuration");
            System.out.print("Votre choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the empty line

            switch (choice) {
                case 1:
                    addRoad(community);
                    break;
                case 2:
                    addOrRemoveRechargeZone(community);
                    break;
                case 3:
                    community.completeConfiguration(); 
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void addRoad(Community community) {
        System.out.print("Nom de la ville de départ : ");
        String city1 = scanner.nextLine();
        System.out.print("Nom de la ville d'arrivée : ");
        String city2 = scanner.nextLine();

        // Ajoute la route à la communauté
        community.addRoad(city1, city2);
    }

    private static void addOrRemoveRechargeZone(Community community) {
        System.out.print("Nom de la ville : ");
        String city = scanner.nextLine();

        System.out.println("\nMenu :");
        System.out.println("1) Ajouter une zone de recharge");
        System.out.println("2) Retirer la zone de recharge");
        System.out.print("Votre choix : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the empty line

        switch (choice) {
            case 1:
                community.addRechargeZone(city);
                break;
            case 2:
                community.removeRechargeZone(city);
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    private static void saveCommunityToFile(Community community) {
        CommunityFileHandler.saveCommunityToFile(community, FILE_PATH);
        System.out.println("La communauté a été sauvegardée dans le fichier : community");
    }

    private static void loadCommunityFromFileAndUpdate(Community community) {
        try {
            System.out.println("Chargement à partir du fichier : community");
            CommunityFileHandler.loadCommunityFromFileAndUpdate(community, FILE_PATH);
            System.out.println("La communauté a été chargée à partir du fichier : community");
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors du chargement de la communauté depuis le fichier.");
            e.printStackTrace();
        }
    }



    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}