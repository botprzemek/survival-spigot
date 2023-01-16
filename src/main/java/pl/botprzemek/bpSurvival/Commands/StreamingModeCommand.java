package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class StreamingModeCommand implements CommandExecutor {

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public StreamingModeCommand(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (pluginManager.isStreamingPlayer(player)) {

            pluginManager.clearStreamingPlayer(player);

            messageManager.sendCommandMessage(player, "streaming.allow");

            messageManager.playPlayerSound(player, "activate");

            return true;

        }

        pluginManager.setStreamingPlayer(player);

        messageManager.sendCommandMessage(player, "streaming.block");

        messageManager.playPlayerSound(player, "activate");

        return true;

    }

}
