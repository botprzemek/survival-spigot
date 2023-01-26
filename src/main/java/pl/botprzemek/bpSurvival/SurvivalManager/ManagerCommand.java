package pl.botprzemek.bpSurvival.SurvivalManager;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.commands.*;

import java.util.Objects;

public class ManagerCommand {

    public ManagerCommand(ManagerSurvival managerSurvival) {

        BpSurvival instance = managerSurvival.getInstance();

        Objects.requireNonNull(instance.getCommand("bpsurvival")).setExecutor(new CommandReload(managerSurvival));

        Objects.requireNonNull(instance.getCommand("spawn")).setExecutor(new CommandSpawn(managerSurvival));

        Objects.requireNonNull(instance.getCommand("vanish")).setExecutor(new CommandVanish(managerSurvival));

        Objects.requireNonNull(instance.getCommand("tp")).setExecutor(new CommandTeleport(managerSurvival));

        Objects.requireNonNull(instance.getCommand("tprequest")).setExecutor(new CommandTeleportRequest(managerSurvival));

        Objects.requireNonNull(instance.getCommand("tpaccept")).setExecutor(new CommandTeleportAccept(managerSurvival));

        Objects.requireNonNull(instance.getCommand("tpdeny")).setExecutor(new CommandTeleportDeny(managerSurvival));

        Objects.requireNonNull(instance.getCommand("home")).setExecutor(new CommandHome(managerSurvival));

        Objects.requireNonNull(instance.getCommand("sethome")).setExecutor(new CommandSetHome(managerSurvival));

        Objects.requireNonNull(instance.getCommand("delhome")).setExecutor(new CommandRemoveHome(managerSurvival));

        Objects.requireNonNull(instance.getCommand("kit")).setExecutor(new CommandKit(managerSurvival));

        Objects.requireNonNull(instance.getCommand("streaming")).setExecutor(new CommandStreamingMode(managerSurvival));

        Objects.requireNonNull(instance.getCommand("message")).setExecutor(new CommandMessage(managerSurvival));

        Objects.requireNonNull(instance.getCommand("reply")).setExecutor(new CommandReply(managerSurvival));

        Objects.requireNonNull(instance.getCommand("summonsupply")).setExecutor(new CommandSummonSupplyDrop(managerSurvival));

        Objects.requireNonNull(instance.getCommand("openinv")).setExecutor(new CommandOpenInv(managerSurvival));

        Objects.requireNonNull(instance.getCommand("openchest")).setExecutor(new CommandOpenChest(managerSurvival));

    }

}
