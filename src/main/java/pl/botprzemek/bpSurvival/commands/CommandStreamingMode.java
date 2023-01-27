package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class CommandStreamingMode implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandStreamingMode(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

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
