package loordgek.extragenarators.network;

public class PowerSync {
    private int energy;
    private int energystore;

    public PowerSync(int energy, int energystore) {
        this.energy = energy;
        this.energystore = energystore;
    }

    public int getEnergy() {
        return energy;
    }

    public int getEnergystore() {
        return energystore;
    }
}
