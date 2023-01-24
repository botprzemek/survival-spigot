package pl.botprzemek.bpSurvival.SurvivalManager;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.Events.*;

public class ManagerEvent {

    public ManagerEvent(ManagerSurvival managerSurvival) {

        BpSurvival instance = managerSurvival.getInstance();

        instance.getServer().getPluginManager().registerEvents(new EventJoinQuit(managerSurvival), instance);

        instance.getServer().getPluginManager().registerEvents(new EventChat(managerSurvival), instance);

        instance.getServer().getPluginManager().registerEvents(new EventMovement(managerSurvival), instance);

        instance.getServer().getPluginManager().registerEvents(new EventBedSleep(managerSurvival), instance);

        instance.getServer().getPluginManager().registerEvents(new EventSpawnProtection(managerSurvival), instance);

    }

}
