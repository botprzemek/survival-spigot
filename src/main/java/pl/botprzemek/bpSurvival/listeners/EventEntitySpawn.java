package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class EventEntitySpawn implements Listener {

    private final ManagerPlugin managerPlugin;

    public EventEntitySpawn(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

    }

    @EventHandler
    public void onEntitySpawnEvent(CreatureSpawnEvent event) {

        if (!managerPlugin.isBlacklistedMobsEnabled()) return;

        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) return;

        if (!managerPlugin.getBlacklistedMobs().contains(event.getEntity().getType().name())) return;

        event.getEntity().remove();

        event.setCancelled(true);

    }

}
