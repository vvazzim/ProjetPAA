package communityapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City {
    private String name;
    private boolean hasRechargeZone;

    public City(String name) {
        this.name = name;
        this.hasRechargeZone = false;
    }

    public String getName() {
        return name;
    }

    public boolean hasRechargeZone() {
        return hasRechargeZone;
    }

    public void addRechargeZone() {
        this.hasRechargeZone = true;
    }

    public void removeRechargeZone() {
        this.hasRechargeZone = false;
    }

    public Set<String> getCitiesWithRechargeZone() {
        Set<String> citiesWithRecharge = new HashSet<>();
        if (hasRechargeZone) {
            citiesWithRecharge.add(name);
        }
        return citiesWithRecharge;
    }
}
