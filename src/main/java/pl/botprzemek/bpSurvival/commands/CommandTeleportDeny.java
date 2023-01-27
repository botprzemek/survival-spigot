package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;


public class CommandTeleportDeny implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandTeleportDeny(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

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
