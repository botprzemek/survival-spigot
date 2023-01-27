package pl.botprzemek.bpSurvival.survival.managers;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.listeners.*;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class ManagerEvent {

    public ManagerEvent(SurvivalPlugin survivalPlugin) {

        BpSurvival instance = survivalPlugin.getInstance();

        instance.getServer().getPluginManager().registerEvents(new EventJoinQuit(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventChat(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventMovement(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventBedSleep(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventSpawnProtection(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventEntitySpawn(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventChangeGamemode(survivalPlugin), instance);

        instance.getServer().getPluginManager().registerEvents(new EventInventoryInteract(survivalPlugin), instance);

    }

}
