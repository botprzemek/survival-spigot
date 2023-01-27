package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class CommandReply implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandReply(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

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
