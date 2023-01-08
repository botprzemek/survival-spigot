package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final ConfigManager configManager;

    private final ProfileManager profileManager;

    public ReloadCommand(SurvivalManager survivalManager) {

        configManager = survivalManager.getConfigManager();

        profileManager = survivalManager.getProfileManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        profileManager.saveProfiles();

        configManager.saveConfigs();

        configManager.loadConfigs();

        profileManager.loadProfiles();

        if (sender instanceof Player player) player.sendMessage("Reloaded");

        return false;

    }
}
