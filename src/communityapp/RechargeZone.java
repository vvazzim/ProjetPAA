package communityapp;

import java.util.HashSet;
import java.util.Set;

public class RechargeZone {
    private Set<String> cities;

    public RechargeZone() {
        this.cities = new HashSet<>();
    }

    public Set<String> getCities() {
        return cities;
    }

    public void addCity(String city) {
        cities.add(city);
    }

    public void removeCity(String city) {
        cities.remove(city);
    }

    public boolean containsCity(String city) {
        return cities.contains(city);
    }
}
