package pl.botprzemek.bpSurvival.SurvivalManager.Profile;

public class Settings {

    private boolean toInventory;

    private boolean minedBlock;

    private int multiplier;

    public Settings(Boolean toInventory, Boolean minedBlock, int multiplier) {

        this.toInventory = toInventory;

        this.minedBlock = minedBlock;

        this.multiplier = multiplier;

    }

    public boolean isMinedBlock() {

        return minedBlock;

    }

    public void setMinedBlock(boolean minedBlock) {

        this.minedBlock = minedBlock;

    }

    public boolean isToInventory() {

        return toInventory;

    }

    public void setToInventory(boolean toInventory) {

        this.toInventory = toInventory;

    }

    public int getMultiplier() {

        return multiplier;

    }

    public void setMultiplier(int multiplier) {

        this.multiplier = multiplier;

    }
}
