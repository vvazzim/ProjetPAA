package communityapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CommunityFileHandler {

    public static void saveCommunityToFile(Community community, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String cityName : community.getCities()) {
                writer.write("ville(" + cityName + ").");
                writer.newLine();
            }

            for (String cityName : community.getCitiesWithRechargeZone()) {
                writer.write("recharge(" + cityName + ").");
                writer.newLine();
            }

            for (String city1 : community.getCities()) {
                for (String city2 : community.getCityNeighbors(city1)) {
                    writer.write("route(" + city1 + "," + city2 + ").");
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de la communauté dans le fichier.");
            e.printStackTrace();
        }
    }

    public static Community loadCommunityFromFile(String filePath) {
        Community community = Community.getInstance();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Supposons que le fichier est bien formaté et que chaque ligne est correcte.
                community.completeConfiguration();
                if (line.startsWith("ville(")) {
                    String cityName = line.substring(6, line.length() - 2);
                    community.addCity(cityName);
                } else if (line.startsWith("recharge(")) {
                    String cityName = line.substring(9, line.length() - 2);
                    community.addRechargeZone(cityName);
                } else if (line.startsWith("route(")) {
                    String[] cities = line.substring(6, line.length() - 2).split(",");
                    community.addRoad(cities[0], cities[1]);
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de la communauté.");
            e.printStackTrace();
        }

        return community;
    }
}
