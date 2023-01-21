package pl.botprzemek.bpSurvival.Events;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class EventManager {

    public EventManager(SurvivalManager survivalManager) {

        BpSurvival instance = survivalManager.getInstance();

        instance.getServer().getPluginManager().registerEvents(new JoinQuitEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new ChatEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new MovementEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new BedSleepEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new SpawnProtectionEvent(survivalManager), instance);

    }

}
