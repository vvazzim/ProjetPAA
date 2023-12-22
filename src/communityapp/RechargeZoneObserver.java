package communityapp;

import java.util.ArrayList;
import java.util.List;

public class RechargeZoneObserver {
    private Community community;

    public RechargeZoneObserver(Community community) {
        this.community = community;
        this.community.addObserver(this);
    }

    public void update(String city1, String city2) {
        System.out.println("Route ajoutée entre " + city1 + " et " + city2);
    }

    public void updateRechargeZone(String city, boolean added) {
        if (added) {
            System.out.println("Zone de recharge ajoutée à " + city);
        } else {
            System.out.println("Zone de recharge retirée de " + city);
        }
    }
}