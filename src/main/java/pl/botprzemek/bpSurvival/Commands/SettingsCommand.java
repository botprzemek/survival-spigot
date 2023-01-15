package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class SettingsCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    public SettingsCommand(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        Profile profile = profileManager.getProfile(player);

        if (args.length == 0) {

            messageManager.sendCommandMessage(player, "settings.message");

            messageManager.sendCommandMessage(player, "settings.level", String.valueOf(profile.getLevel()));

            messageManager.sendCommandMessage(player, "settings.exp", String.valueOf(profile.getExp()));

            messageManager.sendCommandMessage(player, "settings.to-inv", String.valueOf(profile.getSettings().isToInventory()));

            messageManager.sendCommandMessage(player, "settings.mined", String.valueOf(profile.getSettings().isMinedBlock()));

            messageManager.sendCommandMessage(player, "settings.multiplier", String.valueOf(profile.getSettings().getMultiplier()));

            return true;

        }

        profile.setLevel(Integer.parseInt(args[0]));

        profile.getSettings().setToInventory(Boolean.parseBoolean(args[1]));

        profile.getSettings().setMinedBlock(Boolean.parseBoolean(args[2]));

        profile.getSettings().setMultiplier(Integer.parseInt(args[3]));

        return true;

    }
}
