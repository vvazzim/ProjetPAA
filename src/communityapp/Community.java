package communityapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Community {
    private static Community instance;
    private Map<String, City> cities = new HashMap<>();
    private Map<String, List<String>> roadMap = new HashMap<>();
    private Set<String> rechargeZones = new HashSet<>();
    private List<RechargeZoneObserver> observers = new ArrayList<>();
    private boolean configurationCompleted = false;

    private Community() {
        // Constructeur privé pour empêcher l'instanciation directe.
    }

    public static Community getInstance() {
        if (instance == null) {
            instance = new Community();
        }
        return instance;
    }

    public void addCity(String cityName) {
        if (!configurationCompleted) {
            City city = new City(cityName);
            cities.put(cityName, city);
        } else {
            System.out.println("La configuration de la CA est déjà terminée. Vous ne pouvez pas ajouter de nouvelles villes.");
        }
    }

    public void addRoad(String city1, String city2) {
        if (!configurationCompleted) {
            if (cities.containsKey(city1) && cities.containsKey(city2)) {
                if (!roadMap.containsKey(city1)) {
                    roadMap.put(city1, new ArrayList<>());
                }
                roadMap.get(city1).add(city2);

                if (!roadMap.containsKey(city2)) {
                    roadMap.put(city2, new ArrayList<>());
                }
                roadMap.get(city2).add(city1);
            } else {
                System.out.println("Ville(s) non trouvée(s). La route n'a pas été ajoutée.");
            }
        } else {
            System.out.println("La configuration de la CA est déjà terminée. Vous ne pouvez pas ajouter de nouvelles routes.");
        }
    }

    public void completeConfiguration() {
        configurationCompleted = true;
    }

    public void addRechargeZone(String city) {
        if (cities.containsKey(city) && !rechargeZones.contains(city)) {
            // Vérifie si l'ajout est initial ou non
            boolean initialAddition = rechargeZones.isEmpty();
            if (!initialAddition && isAccessibilityConstraintViolated(city, true)) {
                System.out.println("Impossible d'ajouter une zone de recharge à " + city + " (contrainte d'accessibilité violée).");
            } else {
                rechargeZones.add(city);
                notifyObserversRecharge(city, true);
            }
        } else {
            System.out.println("Ville non trouvée ou zone de recharge déjà présente.");
        }
    }

    public void removeRechargeZone(String city) {
        if (rechargeZones.contains(city)) {
            // Vérifie si le retrait viole la contrainte d'accessibilité
            if (isAccessibilityConstraintViolated(city, false)) {
                System.out.println("Impossible de retirer la zone de recharge de " + city + " (contrainte d'accessibilité violée).");
            } else {
                rechargeZones.remove(city);
                notifyObserversRecharge(city, false);
            }
        } else {
            System.out.println("Aucune zone de recharge dans " + city);
        }
    }

    private boolean isAccessibilityConstraintViolated(String city, boolean addingRechargeZone) {
        List<String> neighbors = roadMap.get(city);

        for (String neighbor : neighbors) {
            if (addingRechargeZone) {
                // Si on ajoute une zone de recharge, vérifie que le voisin a toujours accès à une zone de recharge.
                if (!rechargeZones.contains(neighbor)) {
                    return true;
                }
            } else {
                // Si on retire une zone de recharge, vérifie que le voisin a toujours accès à une zone de recharge.
                List<String> reachableRechargeZones = new ArrayList<>(rechargeZones);
                reachableRechargeZones.addAll(neighbors); // Ajoute les voisins actuels comme accessibles

                if (!reachableRechargeZones.contains(neighbor)) {
                    return true;
                }
            }
        }
        return false;
    }


    private void updateAccessibilityConstraint(String city) {
        List<String> neighbors = roadMap.get(city);
        for (String neighbor : neighbors) {
            if (!rechargeZones.contains(neighbor)) {
                // La contrainte d'accessibilité est violée, réajuste la zone de recharge si possible
                rechargeZones.add(neighbor);
                notifyObserversRecharge(neighbor, true);
            }
        }
    }

    public Set<String> getCitiesWithRechargeZone() {
        return rechargeZones;
    }

    public Set<String> getCities() {
        return cities.keySet();
    }

    public Set<String> getCityNeighbors(String city) {
        if (roadMap.containsKey(city)) {
            return new HashSet<>(roadMap.get(city));
        }
        return new HashSet<>();
    }

    // Ajout d'observateurs
    public void addObserver(RechargeZoneObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RechargeZoneObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String city1, String city2) {
        for (RechargeZoneObserver observer : observers) {
            observer.update(city1, city2);
        }
    }

    private void notifyObserversRecharge(String city, boolean added) {
        for (RechargeZoneObserver observer : observers) {
            observer.updateRechargeZone(city, added);
        }
    }
}

