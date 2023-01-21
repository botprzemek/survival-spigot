package pl.botprzemek.bpSurvival.SurvivalManager.Config;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Kit;

import java.util.*;

public class PluginManager {

    private final PluginConfig pluginConfig;

    private Location spawnLocation;

    private Integer timer;

    private Integer limit;

    private final HashMap<UUID, Integer> waitingPlayers;

    private final HashMap<UUID, UUID> teleportingQueue;

    private final HashMap<UUID, UUID> replyPlayers;

    private final List<Kit> kits;

    private final List<UUID> sleepingPlayers;

    private final List<UUID> hiddenPlayers;

    private final List<UUID> streamingPlayers;

    private final List<String> whitelistedBlocks;

    public PluginManager(PluginConfig pluginConfig) {

        this.pluginConfig = pluginConfig;

        waitingPlayers = new HashMap<>();

        teleportingQueue = new HashMap<>();

        replyPlayers = new HashMap<>();

        kits = new ArrayList<>();

        sleepingPlayers = new ArrayList<>();

        hiddenPlayers = new ArrayList<>();

        streamingPlayers = new ArrayList<>();

        whitelistedBlocks = new ArrayList<>();

        loadConfigs();

    }

    public void loadConfigs() {

        setSpawnLocation();

        setKits();

        setWhitelistedBlocks();

        setTimer();

        setLimit();

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

    public void setLimit() {

        limit = pluginConfig.getInt("spawn.limit");

    }

    public Integer getLimit() {

        return limit;

    }

    public void setKits() {

        kits.clear();

        ConfigurationSection kitsSection = pluginConfig.getConfigurationSection("kits");

        if (kitsSection == null) return;

        for (String kitName : kitsSection.getKeys(false)) {

            ConfigurationSection kitSection = kitsSection.getConfigurationSection(kitName);

            if (kitSection == null) return;

            int kitCooldown = kitSection.getInt("cooldown");

            List<ItemStack> kitItems = new ArrayList<>();

            ConfigurationSection itemsSection = kitSection.getConfigurationSection("items");

            if (itemsSection == null) return;

            for (String key : itemsSection.getKeys(false)) {

                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);

                if (itemSection == null) return;

                ItemStack item = OraxenItems.getItemById(itemSection.getString("id")).build();

                item.setAmount(itemSection.getInt("amount"));

                kitItems.add(item);

            }

            kits.add(new Kit(kitName, kitCooldown, kitItems));

        }

    }

    public List<Kit> getKits() {

        return kits;

    }

    public Kit getKit(String kitName) {

        for (Kit kit : kits) {

            if (kit.getName().equals(kitName)) return kit;

        }

        return null;

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

    public void setStreamingPlayer(Player player) {

        streamingPlayers.add(player.getUniqueId());

    }

    public List<UUID> getStreamingPlayers() {

        return streamingPlayers;

    }

    public boolean isStreamingPlayer(Player player) {

        return streamingPlayers.contains(player.getUniqueId());

    }

    public void clearStreamingPlayer(Player player) {

        streamingPlayers.remove(player.getUniqueId());

    }

    public void clearReplyPlayer(Player player) {

        replyPlayers.remove(player.getUniqueId());

    }

    public void setReplyPlayer(Player player, Player target) {

        replyPlayers.put(player.getUniqueId(), target.getUniqueId());

    }

    public UUID getReplyPlayer(Player player) {

        return replyPlayers.get(player.getUniqueId());

    }

    public void setWhitelistedBlocks() {

        for (String materialName : pluginConfig.getStringList("whitelist")) whitelistedBlocks.add(materialName.toUpperCase().replace(" ", "_"));

    }

    public List<String> getWhitelistedBlocks() {

        return whitelistedBlocks;

    }

}
