package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class MessageCommand implements CommandExecutor {

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public MessageCommand(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {

            messageManager.sendCommandMessage(player, "message.invalid");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {

            messageManager.sendCommandMessage(player, "message.offline");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        if (target.equals(player)) {

            messageManager.sendCommandMessage(player, "message.same");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        if (pluginManager.isStreamingPlayer(target)) {

            messageManager.sendCommandMessage(player, "message.deny");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        messageManager.sendMessageToReceiver(pluginManager, player, target, args, 1);

        return true;

    }
}