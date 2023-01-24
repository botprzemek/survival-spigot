package pl.botprzemek.bpSurvival.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.List;

public class EventEntitySpawn implements Listener {

    private final List<String> managerMob;

    public EventEntitySpawn(ManagerSurvival managerSurvival) {

        managerMob = managerSurvival.getPluginManager().getBlacklistedMobs();

    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {

        if (managerMob.contains(event.getEntity().getType().name())) return;

        event.setCancelled(true);

    }

}
