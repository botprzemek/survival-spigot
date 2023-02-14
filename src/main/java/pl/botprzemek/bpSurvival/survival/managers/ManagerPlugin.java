package pl.botprzemek.bpSurvival.survival.managers;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.survival.configs.ConfigPlugin;
import pl.botprzemek.bpSurvival.survival.utils.Kit;

import java.util.*;

public class ManagerPlugin {
    private final ConfigPlugin configPlugin;
    private Location spawnLocation;
    private Integer timer;
    private Integer eventTimer;
    private Integer limit;
    private final HashMap<UUID, Integer> waitingPlayers;
    private final HashMap<UUID, UUID> teleportingQueue;
    private final HashMap<UUID, UUID> replyPlayers;
    private final List<Kit> kits;
    private final List<UUID> sleepingPlayers;
    private final List<UUID> hiddenPlayers;
    private final List<UUID> streamingPlayers;
    private final List<String> blacklistedBlocks;
    private final List<String> blacklistedMobs;
    private boolean isBlacklistedBlocksEnabled;
    private boolean isBlacklistedMobsEnabled;

    public ManagerPlugin(ConfigPlugin configPlugin) {
        this.configPlugin = configPlugin;
        waitingPlayers = new HashMap<>();
        teleportingQueue = new HashMap<>();
        replyPlayers = new HashMap<>();
        kits = new ArrayList<>();
        sleepingPlayers = new ArrayList<>();
        hiddenPlayers = new ArrayList<>();
        streamingPlayers = new ArrayList<>();
        blacklistedBlocks = new ArrayList<>();
        blacklistedMobs = new ArrayList<>();
        isBlacklistedBlocksEnabled = true;
        isBlacklistedMobsEnabled = false;

        loadConfigs();
    }

    public void loadConfigs() {
        setSpawnLocation();
        setKits();
        setBlacklistedBlocksEnabled();
        setBlacklistedMobsEnabled();

        if (isBlacklistedBlocksEnabled) setBlacklistedBlocks();
        if (isBlacklistedMobsEnabled) setBlacklistedMobs();

        setEventTimer();
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

        ConfigurationSection spawnSection = configPlugin.getConfigurationSection("spawn");

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

    public void setEventTimer() {
        eventTimer = configPlugin.getInt("commands.cooldown.event");
    }

    public Integer getEventTimer() {
        return eventTimer;
    }

    public void setTimer() {

        timer = configPlugin.getInt("commands.cooldown.timer");

    }

    public Integer getTimer() {

        return timer;

    }

    public void setLimit() {

        limit = configPlugin.getInt("spawn.limit");

    }

    public Integer getLimit() {

        return limit;

    }

    public void setKits() {

        if (kits.size() != 0) kits.clear();

        ConfigurationSection kitsSection = configPlugin.getConfigurationSection("kits");

        if (kitsSection == null) return;

        for (String kitName : kitsSection.getKeys(false)) {

            ConfigurationSection kitSection = kitsSection.getConfigurationSection(kitName);

            if (kitSection == null) return;

            int kitCooldown = kitSection.getInt("cooldown");

            if (kitCooldown == 0) kitCooldown = 86400;

            List<ItemStack> kitItems = new ArrayList<>();

            ConfigurationSection itemsSection = kitSection.getConfigurationSection("items");

            if (itemsSection == null) return;

            for (String key : itemsSection.getKeys(false)) {

                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);

                if (itemSection == null) return;

                ItemBuilder itemBuilder = OraxenItems.getItemById(itemSection.getString("id"));

                if (itemBuilder == null) return;

                ItemStack item = itemBuilder.build();

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

    public void setBlacklistedBlocksEnabled() {

        isBlacklistedBlocksEnabled = configPlugin.getBoolean("block-blacklist.enabled");

    }

    public boolean isBlacklistedBlocksEnabled() {

        return isBlacklistedBlocksEnabled;

    }

    public void setBlacklistedBlocks() {

        if (blacklistedBlocks.size() != 0) blacklistedBlocks.clear();

        for (String materialName : configPlugin.getStringList("block-blacklist.list")) blacklistedBlocks.add(materialName.toUpperCase().replace(" ", "_"));

    }

    public List<String> getBlacklistedBlocks() {

        return blacklistedBlocks;

    }

    public void setBlacklistedMobsEnabled() {

        isBlacklistedMobsEnabled = configPlugin.getBoolean("mob-blacklist.enabled");

    }

    public boolean isBlacklistedMobsEnabled() {

        return isBlacklistedMobsEnabled;

    }

    public void setBlacklistedMobs() {

        if (blacklistedMobs.size() != 0) blacklistedMobs.clear();

        for (String mobName : configPlugin.getStringList("mob-blacklist.list")) blacklistedMobs.add(mobName.toUpperCase().replace(" ", "_"));

    }

    public List<String> getBlacklistedMobs() {

        return blacklistedMobs;

    }

}
