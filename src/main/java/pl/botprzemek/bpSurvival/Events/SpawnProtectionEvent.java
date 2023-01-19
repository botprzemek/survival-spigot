package pl.botprzemek.bpSurvival.Events;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class SpawnProtectionEvent implements Listener {

    private final PluginManager pluginManager;

    public SpawnProtectionEvent(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

    }

    @EventHandler
    public void onPlayerBlockDestroyEvent(HangingBreakByEntityEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        if (!(event.getRemover() instanceof Player player)) {

            event.setCancelled(true);

            return;

        }

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerBlockDestroyEvent(HangingPlaceEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        Player player = event.getPlayer();

        if (!Objects.requireNonNull(player).getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamageOthersEvent(EntityDamageByEntityEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        if (event.getDamager() instanceof Player player) {

            if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

            event.setCancelled(true);

        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamagedEvent(EntityDamageEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamagedByBlockEvent(EntityDamageByBlockEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;

        if (!pluginManager.getWhitelistedBlocks().contains(clickedBlock.getType().name())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        EntityType entityType = event.getRightClicked().getType();

        if (!pluginManager.getWhitelistedBlocks().contains(entityType.name())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) event.setRespawnLocation(pluginManager.getSpawnLocation());

    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        Player player = event.getPlayer();

        if (!Objects.requireNonNull(player).getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerWaterFill(PlayerBucketFillEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        Player player = event.getPlayer();

        if (!Objects.requireNonNull(player).getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerWaterEmpty(PlayerBucketEmptyEvent event) {

        if (!Objects.equals(pluginManager.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        Player player = event.getPlayer();

        if (!Objects.requireNonNull(player).getGameMode().equals(GameMode.SURVIVAL)) return;

        event.setCancelled(true);

    }

}
