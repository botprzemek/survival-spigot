package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class CommandStreamingMode implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandStreamingMode(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.isStreamingPlayer(player)) {

            managerPlugin.clearStreamingPlayer(player);

            managerMessage.sendCommandMessage(player, "streaming.allow");

            managerMessage.playPlayerSound(player, "activate");

            return true;

        }

        managerPlugin.setStreamingPlayer(player);

        managerMessage.sendCommandMessage(player, "streaming.block");

        managerMessage.playPlayerSound(player, "activate");

        return true;

    }

}
