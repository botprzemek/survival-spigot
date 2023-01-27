package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class EventEntitySpawn implements Listener {

    private final ManagerPlugin managerPlugin;

    public EventEntitySpawn(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

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
