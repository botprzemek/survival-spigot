package pl.botprzemek.bpSurvival.listeners;

import io.th0rgal.oraxen.api.events.OraxenFurnitureBreakEvent;
import io.th0rgal.oraxen.api.events.OraxenFurniturePlaceEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

import java.util.Objects;

public class EventSpawnProtection implements Listener {

    private final ManagerPlugin managerPlugin;

    public EventSpawnProtection(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

    }

    @EventHandler
    public void onPlayerBlockDestroyEvent(BlockBreakEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerBlockDestroyEvent(HangingBreakByEntityEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        if (!(event.getRemover() instanceof Player player)) {

            event.setCancelled(true);

            return;

        }

        if (player.hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerBlockDestroyEvent(HangingPlaceEvent event) {

        if (event.getPlayer() == null) return;

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamageOthersEvent(EntityDamageByEntityEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getDamager().getWorld())) return;

        if (event.getDamager() instanceof Player player) {

            if (player.hasPermission("bpsurvival.protection")) return;

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onPlayerDamagedEvent(EntityDamageEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        if (!(event.getEntity() instanceof Player player)) return;

        if (player.hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamagedByBlockEvent(EntityDamageByBlockEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getEntity().getWorld())) return;

        if (event.getEntity() instanceof Player player) {

            if (player.hasPermission("bpsurvival.protection")) return;

            event.setCancelled(true);

        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;

        if (!managerPlugin.getBlacklistedBlocks().contains(clickedBlock.getType().name())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().hasMetadata("NPC")) return;

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        EntityType entityType = event.getRightClicked().getType();

        if (!managerPlugin.isBlacklistedBlocksEnabled()) return;

        if (!managerPlugin.getBlacklistedBlocks().contains(entityType.name())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {

        if (event.getPlayer().getBedSpawnLocation() == null) event.setRespawnLocation(managerPlugin.getSpawnLocation());

    }

    @EventHandler
    public void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerWaterFillEvent(PlayerBucketFillEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerWaterEmptyEvent(PlayerBucketEmptyEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerTakeItemsEvent(PlayerArmorStandManipulateEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onOraxenBlocksBreakEvent(OraxenFurnitureBreakEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onOraxenBlocksPlaceEvent(OraxenFurniturePlaceEvent event) {

        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;

        if (event.getPlayer().hasPermission("bpsurvival.protection")) return;

        event.setCancelled(true);

    }

}
