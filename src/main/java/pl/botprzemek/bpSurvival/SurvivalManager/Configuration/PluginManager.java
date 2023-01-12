package pl.botprzemek.bpSurvival.SurvivalManager.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.PluginConfig;

import java.util.*;

public class PluginManager {

    private final PluginConfig pluginConfig;

    private Location spawnLocation;

    private  Integer timer;

    private final HashMap<UUID, Integer> waitingPlayers;

    private final List<UUID> sleepingPlayers;

    public PluginManager(PluginConfig pluginConfig) {

        this.pluginConfig = pluginConfig;

        waitingPlayers = new HashMap<>();

        sleepingPlayers = new ArrayList<>();

        setTimer();

        setSpawnLocation();

    }

    public void setSpawnLocation() {

        ConfigurationSection spawnSection = pluginConfig.getConfigurationSection("spawn");

        if (spawnSection == null) return;

        World world = Bukkit.getWorld(Objects.requireNonNull(spawnSection.getString("world")));

        double x = spawnSection.getDouble("x");
        double y = spawnSection.getDouble("y");
        double z = spawnSection.getDouble("z");

        float yaw = (float) spawnSection.getDouble("yaw");
        float pitch = (float) spawnSection.getDouble("pitch");

        spawnLocation = new Location(world, x, y, z, yaw, pitch);

    }

    public Location getSpawnLocation() {

        return spawnLocation;

    }

    public void setTimer() {

        timer = pluginConfig.getInt("commands.spawn.timer");

    }

    public Integer getTimer() {

        return timer;

    }

    public HashMap<UUID, Integer> getWaitingPlayers() {

        return waitingPlayers;

    }

    public int getWaitingPlayer(Player player) {

        try {

            return waitingPlayers.get(player.getUniqueId());

        }

        catch (NullPointerException error) {

            return -1;

        }

    }

    public void setWaitingPlayer(Player player, int time) {

        waitingPlayers.put(player.getUniqueId(), time);

    }

    public void clearWaitingPlayer(Player player) {

        waitingPlayers.remove(player.getUniqueId());

    }

    public List<UUID> getSleepingPlayers() {

        return sleepingPlayers;

    }

    public void setSleepingPlayer(Player player) {

        sleepingPlayers.add(player.getUniqueId());

    }

    public void clearSleepingPlayer(Player player) {

        sleepingPlayers.remove(player.getUniqueId());

    }

}
