package pl.botprzemek.bpSurvival.Commands;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class CommandManager {

    public CommandManager(SurvivalManager survivalManager) {

        BpSurvival instance = survivalManager.getInstance();

        Objects.requireNonNull(instance.getCommand("bpsurvival")).setExecutor(new ReloadCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("test")).setExecutor(new SettingsCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("spawn")).setExecutor(new SpawnCommand(survivalManager));

    }

}
