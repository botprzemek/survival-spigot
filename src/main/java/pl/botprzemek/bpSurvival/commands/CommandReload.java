package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.managers.*;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class CommandReload implements CommandExecutor {

    private final ManagerConfig managerConfig;
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;
    private final ManagerPlugin managerPlugin;
    private final ManagerGui managerGui;

    public CommandReload(SurvivalPlugin survivalPlugin) {
        managerConfig = survivalPlugin.getManagerConfig();
        managerProfile = survivalPlugin.getManagerProfile();
        managerMessage = survivalPlugin.getManagerMessage();
        managerPlugin = survivalPlugin.getManagerPlugin();
        managerGui = survivalPlugin.getManagerGui();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            managerProfile.saveProfiles();
            managerConfig.saveConfigs();
            managerConfig.loadConfigs();
            managerProfile.loadProfiles();
            managerPlugin.loadConfigs();
            managerGui.loadGuis();

            if (!(sender instanceof Player player)) return true;

            managerMessage.sendCommandMessage(player, "reload.success");
            managerMessage.playPlayerSound(player, "activate");

            return true;
        }
        catch (Exception error) {
            if (!(sender instanceof Player player)) return false;

            managerMessage.sendCommandMessage(player, "reload.failed");
            managerMessage.playPlayerSound(player, "error");

            return false;

        }
    }
}
