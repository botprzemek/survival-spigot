package pl.botprzemek.bpSurvival.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.DropManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.Items;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Settings;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BlockDestroyEvent implements Listener {

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    private final DropManager dropManager;

    private final PluginManager pluginManager;

    public BlockDestroyEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

        dropManager = survivalManager.getDropManager();

        pluginManager = survivalManager.getPluginManager();

    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent event) {

        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;

        if (event.getItems().size() == 0) return;

        if (!dropManager.getBlocks().contains(event.getItems().get(0).getItemStack().getType())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {

        if (Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) {

            if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;

            event.setCancelled(true);

        }

        if (!dropManager.getBlocks().contains(event.getBlock().getType())) return;

        Player player = event.getPlayer();

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setDropItems(false);

        Profile profile = profileManager.getProfile(player);

        Settings settings = profile.getSettings();

        List<ItemStack> drops = new ArrayList<>();

        int multiplier = (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) ? settings.getMultiplier() * player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) : settings.getMultiplier();

        if (settings.isMinedBlock() && event.getBlock().getDrops().size() != 0) drops.add(event.getBlock().getDrops(player.getItemInUse()).stream().toList().get(0));

        Location location = event.getBlock().getLocation();

        ThreadLocalRandom random = ThreadLocalRandom.current();

        Items items = dropManager.getItems().get(random.nextInt(dropManager.getItems().size()));

        if (items.shouldDrop(random)) drops.add(items.createItem(random, multiplier));

        Inventory inventory = player.getInventory();

        World world = player.getWorld();

        if (!settings.isToInventory()) {

            for (ItemStack item : drops) { world.dropItemNaturally(location, item); }

            levelUp(player, profile, settings);

            return;

        }

        for (ItemStack item : drops) {

            if (inventory.firstEmpty() != -1) inventory.addItem(item);

            else world.dropItemNaturally(location, item);

        }

        levelUp(player, profile, settings);

    }

    private void levelUp(Player player, Profile profile, Settings settings) {

        profile.setExp(profile.getExp() + settings.getMultiplier());

        int level = profile.getLevel();

        if (profile.getExp() < 50 * level) return;

        profile.setLevel(level + 1);

        messageManager.sendTitle(player, "level.up.mining");

        messageManager.playPlayerSound(player, "success");

    }

}
