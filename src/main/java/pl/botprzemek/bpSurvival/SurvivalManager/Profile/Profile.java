package pl.botprzemek.bpSurvival.SurvivalManager.Profile;

import org.bukkit.Location;

import java.util.HashMap;

public class Profile {

    private int level;

    private int exp;

    private final Settings settings;

    private final HashMap<String, Location> homes;

    private final HashMap<String, Long> cooldowns;

    public Profile(int level, int exp, Settings settings, HashMap<String, Location> homes, HashMap<String, Long> cooldowns) {

        this.level = level;

        this.exp = exp;

        this.settings = settings;

        this.homes = homes;

        this.cooldowns = cooldowns;

    }

    public Settings getSettings() {

        return settings;

    }

    public void setHome(String homeName, Location location) {

        homes.put(homeName, location);

    }

    public HashMap<String, Location> getHomes() {

        return homes;

    }

    public void setCooldown(String cooldown, Long time) {

        cooldowns.put(cooldown, time);

    }

    public HashMap<String, Long> getCooldowns() {

        return cooldowns;

    }

    public void clearCooldown(String cooldownName) {

        cooldowns.remove(cooldownName);

    }

    public void setLevel(int level) {

        this.level = level;

        this.exp = 0;

    }

    public int getLevel() {

        return level;

    }

    public int getExp() {

        return exp;

    }

    public void setExp(int exp) {

        this.exp = exp;

    }

}
