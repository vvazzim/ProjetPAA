package communityapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Community community = Community.getInstance();
        RechargeZoneObserver observer = new RechargeZoneObserver(community);

        // Chemin du fichier
        String filePath = "C:\\Users\\TRETEC\\eclipse-workspace\\PAAPro\\src\\communityapp\\community.txt";

        while (true) {
            clearConsole(); // Efface la console

            System.out.println("Bienvenue dans le programme de configuration de la Communauté d'Agglomération.");
            System.out.print("Combien de villes dans la CA ? ");
            int numCities = scanner.nextInt();
            scanner.nextLine();  // Pour consommer la ligne vide

            // Créez les villes et ajoutez-les à la communauté
            for (int i = 0; i < numCities; i++) {
                System.out.print("Nom de la ville " + (char) ('A' + i) + ": ");
                String cityName = scanner.nextLine();
                community.addCity(cityName);
            }

            community.completeConfiguration();

            while (true) {
                clearConsole(); // Efface la console

                System.out.println("\nMenu :");
                System.out.println("1) Ajouter une route");
                System.out.println("2) Terminer la configuration");
                System.out.print("Votre choix : ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Pour consommer la ligne vide

                if (choice == 1) {
                    System.out.print("Nom de la ville de départ : ");
                    String city1 = scanner.nextLine();
                    System.out.print("Nom de la ville d'arrivée : ");
                    String city2 = scanner.nextLine();

                    // Ajoute la route à la communauté
                    community.addRoad(city1, city2);
                } else if (choice == 2) {
                    break;
                } else {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

            System.out.println("Configuration de la CA terminée.");

            while (true) {
                clearConsole(); // Efface la console

                System.out.println("\nMenu :");
                System.out.println("1) Ajouter une zone de recharge");
                System.out.println("2) Retirer une zone de recharge");
                System.out.println("3) Sauvegarder la communauté dans un fichier");
                System.out.println("4) Charger la communauté depuis un fichier");
                System.out.println("5) Terminer");
                System.out.print("Votre choix : ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Pour consommer la ligne vide

                if (choice == 1) {
                    System.out.print("Nom de la ville où ajouter une zone de recharge : ");
                    String city = scanner.nextLine();
                    community.addRechargeZone(city);
                } else if (choice == 2) {
                    System.out.print("Nom de la ville où retirer une zone de recharge : ");
                    String city = scanner.nextLine();
                    community.removeRechargeZone(city);
                } else if (choice == 3) {
                    // Sauvegarde de la communauté dans le fichier
                    CommunityFileHandler.saveCommunityToFile(community, filePath);
                    System.out.println("Communauté sauvegardée avec succès !");
                } else if (choice == 4) {
                    // Chargement de la communauté depuis le fichier
                    community = CommunityFileHandler.loadCommunityFromFile(filePath);
                    System.out.println("Communauté chargée avec succès !");
                } else if (choice == 5) {
                    break;
                } else {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

            System.out.println("Fin du programme");
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
