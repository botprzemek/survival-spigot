package pl.botprzemek.bpSurvival.Commands;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class CommandManager {

    public CommandManager(SurvivalManager survivalManager) {

        BpSurvival instance = survivalManager.getInstance();

        Objects.requireNonNull(instance.getCommand("bpsurvival")).setExecutor(new ReloadCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("settings")).setExecutor(new SettingsCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("spawn")).setExecutor(new SpawnCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("vanish")).setExecutor(new VanishCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("tprequest")).setExecutor(new TeleportRequestCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("tpaccept")).setExecutor(new TeleportAcceptCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("tpdeny")).setExecutor(new TeleportDenyCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("home")).setExecutor(new HomeCommand(survivalManager));

        Objects.requireNonNull(instance.getCommand("sethome")).setExecutor(new SetHomeCommand(survivalManager));

    }

}
