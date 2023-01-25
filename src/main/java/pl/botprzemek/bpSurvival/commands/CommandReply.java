package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class CommandReply implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandReply(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {

            managerMessage.sendCommandMessage(player, "message.invalid");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Player target = Bukkit.getPlayer(managerPlugin.getReplyPlayer(player));

        if (target == null) {

            managerMessage.sendCommandMessage(player, "message.offline");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (managerPlugin.isStreamingPlayer(target)) {

            managerMessage.sendCommandMessage(player, "message.deny");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        managerMessage.sendMessageToReceiver(managerPlugin, player, target, args, 0);

        return true;

    }
}
