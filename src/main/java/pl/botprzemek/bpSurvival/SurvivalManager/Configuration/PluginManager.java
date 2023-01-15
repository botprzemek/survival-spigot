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

    private final HashMap<UUID, UUID> teleportingQueue;

    private final List<UUID> sleepingPlayers;

    private final List<UUID> hiddenPlayers;

    private final HashMap<UUID, Long> minersBoostCooldown;

    public PluginManager(PluginConfig pluginConfig) {

        this.pluginConfig = pluginConfig;

        waitingPlayers = new HashMap<>();

        teleportingQueue = new HashMap<>();

        sleepingPlayers = new ArrayList<>();

        hiddenPlayers = new ArrayList<>();

        minersBoostCooldown = new HashMap<>();

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

    public Integer getWaitingPlayer(Player player) {

        if (waitingPlayers.size() == 0) return -1;

        if (waitingPlayers.get(player.getUniqueId()) == null) return -1;

        return waitingPlayers.get(player.getUniqueId());

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

    public HashMap<UUID, UUID> getTeleportingQueuePlayers() {

        return teleportingQueue;

    }

    public UUID getTeleportingQueueRequestedPlayer(Player player) {

        return teleportingQueue.get(player.getUniqueId());

    }

    public void setTeleportingQueuePlayer(Player player, Player requestedPlayer) {

        teleportingQueue.put(player.getUniqueId(), requestedPlayer.getUniqueId());

    }

    public void clearTeleportingQueuePlayer(Player player) {

        teleportingQueue.remove(player.getUniqueId());

    }

    public void setHiddenPlayer(Player player) {

        hiddenPlayers.add(player.getUniqueId());

    }

    public List<UUID> getHiddenPlayers() {

        return hiddenPlayers;

    }

    public boolean isHiddenPlayer(Player player) {

        return hiddenPlayers.contains(player.getUniqueId());

    }

    public void clearHiddenPlayer(Player player) {

        hiddenPlayers.remove(player.getUniqueId());

    }

    public HashMap<UUID, Long> getMinersBoostCooldown() {

        return minersBoostCooldown;

    }

    public Long getMinerBoostCooldown(Player player) {

        return minersBoostCooldown.get(player.getUniqueId());

    }

    public void setMinerBoostCooldown(Player player, Long newTime) {

        minersBoostCooldown.put(player.getUniqueId(), newTime);

    }

    public void clearMinerBoostCooldown(Player player) {

        minersBoostCooldown.remove(player.getUniqueId());

    }

}
