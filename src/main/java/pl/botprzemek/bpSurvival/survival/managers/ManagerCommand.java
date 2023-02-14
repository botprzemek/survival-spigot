package pl.botprzemek.bpSurvival.survival.managers;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.commands.*;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

import java.util.Objects;

public class ManagerCommand {
    public ManagerCommand(SurvivalPlugin survivalPlugin) {
        BpSurvival instance = survivalPlugin.getInstance();
        Objects.requireNonNull(instance.getCommand("bpsurvival")).setExecutor(new CommandReload(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("spawn")).setExecutor(new CommandSpawn(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("vanish")).setExecutor(new CommandVanish(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("tp")).setExecutor(new CommandTeleport(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("tprequest")).setExecutor(new CommandTeleportRequest(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("tpaccept")).setExecutor(new CommandTeleportAccept(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("tpdeny")).setExecutor(new CommandTeleportDeny(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("home")).setExecutor(new CommandHome(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("sethome")).setExecutor(new CommandSetHome(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("delhome")).setExecutor(new CommandRemoveHome(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("kit")).setExecutor(new CommandKit(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("streaming")).setExecutor(new CommandStreamingMode(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("message")).setExecutor(new CommandMessage(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("reply")).setExecutor(new CommandReply(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("openinv")).setExecutor(new CommandOpenInv(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("openchest")).setExecutor(new CommandOpenChest(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("opengui")).setExecutor(new CommandOpenGui(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("letter")).setExecutor(new CommandLetter(survivalPlugin));
        Objects.requireNonNull(instance.getCommand("lottery")).setExecutor(new CommandLottery(survivalPlugin));
    }
}
