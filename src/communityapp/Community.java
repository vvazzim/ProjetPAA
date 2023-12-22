package communityapp;

import java.util.*;

public class Community {
    private static Community instance;
    private Map<String, City> cities = new HashMap<>();
    private Map<String, List<String>> roadMap = new HashMap<>();
    private List<RechargeZone> rechargeZones = new ArrayList<>();
    boolean configurationCompleted = false;

    private Set<String> cityNames = new HashSet<>();

    private Community() {
        // Private constructor to prevent direct instantiation.
    }

    public static Community getInstance() {
        if (instance == null) {
            instance = new Community();
        }
        return instance;
    }

    public void addCity(String cityName) {
        if (!configurationCompleted) {
            if (cityNames.stream().anyMatch(existingName -> existingName.equalsIgnoreCase(cityName))) {
                System.out.println("La ville " + cityName + " existe déjà.");
                return;
            }

            cityNames.add(cityName);
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

                notifyObservers(city1, city2); // Notify observers that a road has been added
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
        if (cities.containsKey(city)) {
            City cityObj = cities.get(city);

            // Vérifie si l'ajout est initial ou non
            boolean initialAddition = cityObj.hasRechargeZone();
            if (!initialAddition && isAccessibilityConstraintViolated(city, true)) {
                System.out.println("Impossible d'ajouter une zone de recharge à " + city + " (contrainte d'accessibilité violée).");
            } else {
                RechargeZone rechargeZone = createOrGetRechargeZone(city);
                cityObj.addRechargeZone(rechargeZone);
                notifyObserversRecharge(city, true);
                updateAccessibilityConstraint(city);
            }
        } else {
            System.out.println("Ville non trouvée.");
        }
    }

    public void removeRechargeZone(String city) {
        if (cities.containsKey(city)) {
            City cityObj = cities.get(city);

            // Vérifie si la ville a effectivement une zone de recharge
            if (cityObj.hasRechargeZone()) {
                // Vérifie si le retrait viole la contrainte d'accessibilité
                if (isAccessibilityConstraintViolated(city, false)) {
                    System.out.println("Impossible de retirer la zone de recharge de " + city + " (contrainte d'accessibilité violée).");
                } else {
                    RechargeZone rechargeZone = cityObj.getRechargeZone();
                    cityObj.removeRechargeZone();
                    notifyObserversRecharge(city, false);
                    updateAccessibilityConstraint(city);  // Mise à jour de la contrainte d'accessibilité après le retrait

                    // Supprime la zone de recharge si elle n'est associée à aucune ville
                    if (rechargeZone.getCities().isEmpty()) {
                        removeRechargeZone(rechargeZone);
                    }
                }
            } else {
                System.out.println("La ville " + city + " n'a pas de zone de recharge à retirer.");
            }
        } else {
            System.out.println("Ville non trouvée.");
        }
    }

 // Méthode pour créer une nouvelle zone de recharge ou obtenir la zone existante
    private RechargeZone createOrGetRechargeZone(String city) {
        for (RechargeZone rz : rechargeZones) {
            if (rz.containsCity(city)) {
                return rz;
            }
        }

        // Si la ville n'est pas associée à une zone, crée une nouvelle zone
        RechargeZone newRechargeZone = new RechargeZone();
        newRechargeZone.addCity(city);
        rechargeZones.add(newRechargeZone);
        return newRechargeZone;
    }


    // Méthode pour supprimer une zone de recharge
    private void removeRechargeZone(RechargeZone rechargeZone) {
        rechargeZones.remove(rechargeZone);
    }

    private boolean isAccessibilityConstraintViolated(String city, boolean addingRechargeZone) {
        List<String> neighbors = roadMap.get(city);

        // Vérifie si la liste des voisins est nulle
        if (neighbors == null) {
            return false;
        }

        for (String neighbor : neighbors) {
            City neighborCity = cities.get(neighbor);

            if (!addingRechargeZone) {
                // Si on retire une zone de recharge, vérifie que le voisin a toujours accès à une zone de recharge.
                if (!neighborCity.hasRechargeZone()) {
                    return true;
                }
            } else {
                // Si on ajoute une zone de recharge, ne fait rien ici
            }
        }
        return false;
    }


    private void updateAccessibilityConstraint(String city) {
        List<String> neighbors = roadMap.get(city);

        // Vérifie si la liste des voisins est nulle
        if (neighbors == null) {
            return;
        }

        for (String neighbor : neighbors) {
            City neighborCity = cities.get(neighbor);

            if (!neighborCity.hasRechargeZone()) {
                // La contrainte d'accessibilité est violée, réajuste la zone de recharge si possible
                RechargeZone rechargeZone = createOrGetRechargeZone(neighbor);
                neighborCity.addRechargeZone(rechargeZone);
                notifyObserversRecharge(neighbor, true);
            }
        }
    }

    public Set<String> getCitiesWithRechargeZone() {
        Set<String> citiesWithRecharge = new HashSet<>();
        for (RechargeZone rechargeZone : rechargeZones) {
            citiesWithRecharge.addAll(rechargeZone.getCities());
        }
        return citiesWithRecharge;
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

    private List<RechargeZoneObserver> observers = new ArrayList<>();

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

    public void resetConfiguration() {
        configurationCompleted = false;
        roadMap.clear();
        rechargeZones.clear();
        observers.clear();
    }
    public Map<String, List<String>> getRoadMap() {
        return roadMap;
    }



}
