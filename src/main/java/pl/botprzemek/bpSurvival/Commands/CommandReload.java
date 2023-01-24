package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerConfig;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class CommandReload implements CommandExecutor {

    private final ManagerConfig managerConfig;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    private final ManagerPlugin managerPlugin;

    public CommandReload(ManagerSurvival managerSurvival) {

        managerConfig = managerSurvival.getConfigManager();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

        managerPlugin = managerSurvival.getPluginManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        try {

            managerProfile.saveProfiles();

            managerConfig.saveConfigs();

            managerConfig.loadConfigs();

            managerProfile.loadProfiles();

            managerPlugin.loadConfigs();

            if (sender instanceof Player player) {

                managerMessage.sendCommandMessage(player, "reload.success");

                managerMessage.playPlayerSound(player, "activate");

            }

        }

        catch (Exception error) {

            if (sender instanceof Player player) {

                managerMessage.sendCommandMessage(player, "reload.failed");

                managerMessage.playPlayerSound(player, "error");

            }

        }

        return false;

    }

}
