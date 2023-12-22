package communityapp;

import java.io.*;
import java.util.List;
import java.util.Map;

public class CommunityFileHandler {

	public static void saveCommunityToFile(Community community, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            // Save cities
            for (String cityName : community.getCities()) {
                writer.println("ville(" + cityName + ").");
            }

            // Save roads
            for (Map.Entry<String, List<String>> entry : community.getRoadMap().entrySet()) {
                String city1 = entry.getKey();
                for (String city2 : entry.getValue()) {
                    writer.println("route(" + city1 + "," + city2 + ").");
                }
            }

            // Save recharge zones
            for (String city : community.getCitiesWithRechargeZone()) {
                writer.println("recharge(" + city + ").");
            }

            System.out.println("La communauté a été sauvegardée dans le fichier : community");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCommunityFromFileAndUpdate(Community community, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLineAndUpdateCommunity(line, community);
            }
            System.out.println("La communauté a été chargée à partir du fichier : community");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLineAndUpdateCommunity(String line, Community community) {
        if (line.startsWith("ville(")) {
            // Extract city name from the line and convert to lowercase
            String cityName = line.substring(6, line.length() - 2).toLowerCase();
            community.addCity(cityName);
        } else if (line.startsWith("route(")) {
            // Extract city names from the line and convert to lowercase
            String[] parts = line.substring(6, line.length() - 2).split(",");
            if (parts.length == 2) {
                community.addRoad(parts[0].toLowerCase(), parts[1].toLowerCase());
            }
        } else {
            System.out.println("Unrecognized line: " + line);
        }
    }
}
