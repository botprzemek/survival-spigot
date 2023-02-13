package pl.botprzemek.bpSurvival.survival.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Profile {
    private final HashMap<String, Location> homes;
    private final HashMap<String, Long> cooldowns;
    private final HashMap<UUID, Boolean> letters;

    public Profile(HashMap<String, Location> homes, HashMap<String, Long> cooldowns, HashMap<UUID, Boolean> letters) {
        this.homes = homes;
        this.cooldowns = cooldowns;
        this.letters = letters;
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

    public void setLetter(Player player, boolean value) {
        letters.put(player.getUniqueId(), value);
    }

    public HashMap<UUID, Boolean> getLetters() {
        return letters;
    }
}
