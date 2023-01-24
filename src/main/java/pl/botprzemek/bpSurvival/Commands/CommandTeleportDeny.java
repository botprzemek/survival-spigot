package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;


public class CommandTeleportDeny implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandTeleportDeny(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (!managerPlugin.getTeleportingQueuePlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "tp.deny.empty");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        managerPlugin.clearTeleportingQueuePlayer(player);

        managerMessage.sendCommandMessage(player, "tp.deny.clear");

        managerMessage.playPlayerSound(player, "activate");

        return true;

    }

}
