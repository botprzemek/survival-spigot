package pl.botprzemek.bpSurvival.SurvivalManager.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.PluginConfig;

import java.util.HashMap;
import java.util.UUID;

public class PluginManager {

    private final PluginConfig pluginConfig;

    private Location spawnLocation;

    private  Integer timer;

    private final HashMap<UUID, Integer> waitingPlayers;

    public PluginManager(PluginConfig pluginConfig) {

        this.pluginConfig = pluginConfig;

        waitingPlayers = new HashMap<>();

        setTimer();

        setSpawnLocation();

    }

    public void setSpawnLocation() {

        ConfigurationSection spawnSection = pluginConfig.getConfigurationSection("spawn");

        if (spawnSection == null) return;

        World world = Bukkit.getWorld(spawnSection.getString("world"));

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

        player.sendMessage("Cleaned data...");

        waitingPlayers.remove(player.getUniqueId());

    }

}
