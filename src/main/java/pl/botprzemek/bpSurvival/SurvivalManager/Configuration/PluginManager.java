package pl.botprzemek.bpSurvival.SurvivalManager.Configuration;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.PluginConfig;

import java.util.*;

public class PluginManager {

    private final PluginConfig pluginConfig;

    private Location spawnLocation;

    private  Integer timer;

    private final HashMap<UUID, Integer> waitingPlayers;

    private final HashMap<UUID, UUID> teleportingQueue;

    private final HashMap<String, List<ItemStack>> kits;

    private final List<UUID> sleepingPlayers;

    private final List<UUID> hiddenPlayers;

    public PluginManager(PluginConfig pluginConfig) {

        this.pluginConfig = pluginConfig;

        waitingPlayers = new HashMap<>();

        teleportingQueue = new HashMap<>();

        kits = new HashMap<>();

        sleepingPlayers = new ArrayList<>();

        hiddenPlayers = new ArrayList<>();

        setTimer();

        setSpawnLocation();

        setKits();

    }

    public boolean inventoryHaveSpace(Player player, int size) {

        int isEmpty = -5;

        for (ItemStack item : player.getInventory().getContents()) {

            if (item == null || item.getType() == Material.AIR) isEmpty++;

        }

        return isEmpty >= size;

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

        timer = pluginConfig.getInt("commands.cooldown.timer");

    }

    public Integer getTimer() {

        return timer;

    }

    public void setKits() {

        ConfigurationSection kitsSection = pluginConfig.getConfigurationSection("kits");

        if (kitsSection == null) return;

        for (String kitName : kitsSection.getKeys(false)) {

            ConfigurationSection kitSection = kitsSection.getConfigurationSection(kitName);

            if (kitSection == null) return;

            List<ItemStack> kitItems = new ArrayList<>();

            for (String key : kitSection.getKeys(false)) {

                ConfigurationSection itemSection = kitSection.getConfigurationSection(key);

                if (itemSection == null) return;

                ItemStack item = OraxenItems.getItemById(itemSection.getString("id")).build();

                item.setAmount(itemSection.getInt("amount"));

                kitItems.add(item);

            }

            kits.put(kitName, kitItems);

        }

    }

    public List<ItemStack> getKit(String kitName) {

        return kits.get(kitName);

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

}
