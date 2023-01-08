package pl.botprzemek.bpSurvival.Events;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class EventManager {

    public EventManager(SurvivalManager survivalManager) {

        BpSurvival instance = survivalManager.getInstance();

        instance.getServer().getPluginManager().registerEvents(new PlayerJoinQuitEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new BlockDestroyEvent(survivalManager), instance);

        instance.getServer().getPluginManager().registerEvents(new PlayerMovementEvent(survivalManager), instance);

    }

}
