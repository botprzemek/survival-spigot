package pl.botprzemek.bpSurvival.Commands;

import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SettingsCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    public SettingsCommand(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        profileManager.getProfile(player).setLevel(Integer.parseInt(args[0]));

        profileManager.getProfile(player).getSettings().setToInventory(Boolean.parseBoolean(args[1]));

        profileManager.getProfile(player).getSettings().setMinedBlock(Boolean.parseBoolean(args[2]));

        profileManager.getProfile(player).getSettings().setMultiplier(Integer.parseInt(args[3]));

        return true;

    }
}
