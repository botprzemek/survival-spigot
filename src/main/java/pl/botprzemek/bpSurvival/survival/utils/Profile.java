package pl.botprzemek.bpSurvival.survival.utils;

import org.bukkit.Location;

import java.util.HashMap;

public class Profile {

    private final HashMap<String, Location> homes;

    private final HashMap<String, Long> cooldowns;

    public Profile(HashMap<String, Location> homes, HashMap<String, Long> cooldowns) {

        this.homes = homes;

        this.cooldowns = cooldowns;

    }

    public void setHome(String homeName, Location location) {

        homes.put(homeName, location);

    }

    public void removeHome(String homeName) {

        homes.remove(homeName);

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

}
