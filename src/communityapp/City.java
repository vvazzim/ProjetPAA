package communityapp;

import java.util.HashSet;
import java.util.Set;

public class City {
    private String name;
    private RechargeZone rechargeZone; // Référence à la zone de recharge associée

    public City(String name) {
        this.name = name;
        this.rechargeZone = null; // Initialisez à null car la ville n'est pas encore associée à une zone de recharge
    }

    public String getName() {
        return name;
    }

    public boolean hasRechargeZone() {
        return rechargeZone != null;
    }

    public RechargeZone getRechargeZone() {
        return rechargeZone;
    }

    public void addRechargeZone(RechargeZone rechargeZone) {
        this.rechargeZone = rechargeZone;
        rechargeZone.addCity(name);
    }

    public void removeRechargeZone() {
        if (rechargeZone != null) {
            rechargeZone.removeCity(name);
            this.rechargeZone = null;
        }
    }

    public Set<String> getRechargeZones() {
        Set<String> rechargeZones = new HashSet<>();
        if (hasRechargeZone()) {
            rechargeZones.addAll(rechargeZone.getCities());
        }
        return rechargeZones;
    }
}
