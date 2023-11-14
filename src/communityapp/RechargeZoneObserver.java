package communityapp;

public class RechargeZoneObserver {
    private Community community;

    public RechargeZoneObserver(Community community) {
        this.community = community;
        this.community.addObserver(this);
    }

    // Méthode pour mettre à jour lorsqu'une route est ajoutée
    public void update(String city1, String city2) {
        System.out.println("Route ajoutée entre " + city1 + " et " + city2);
        // Vous pouvez ajouter d'autres actions d'observation ici.
    }

    // Méthode pour mettre à jour lorsqu'une zone de recharge est ajoutée ou retirée
    public void updateRechargeZone(String city, boolean added) {
        if (added) {
            System.out.println("Zone de recharge ajoutée à " + city);
        } else {
            System.out.println("Zone de recharge retirée de " + city);
        }
    }
}
