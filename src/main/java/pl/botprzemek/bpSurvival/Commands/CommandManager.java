package pl.botprzemek.bpSurvival.Commands;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class CommandManager {

    public CommandManager(SurvivalManager survivalManager) {

        BpSurvival instance = survivalManager.getInstance();

        instance.getCommand("bpsurvival").setExecutor(new ReloadCommand(survivalManager));

        instance.getCommand("test").setExecutor(new TestCommand(survivalManager));

        instance.getCommand("spawn").setExecutor(new SpawnCommand(survivalManager));

    }

}
