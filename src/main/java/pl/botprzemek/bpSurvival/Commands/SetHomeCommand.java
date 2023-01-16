package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class SetHomeCommand implements CommandExecutor {

    private final PluginManager pluginManager;

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    public SetHomeCommand(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (pluginManager.getWaitingPlayers().containsKey(player.getUniqueId())) {

            messageManager.sendCommandMessage(player, "teleport.already");

            return false;

        }

        Profile profile = profileManager.getProfile(player);

        String homeName = (args.length == 0) ? "home" : args[0];

        profile.setHome(homeName, player.getLocation());

        messageManager.sendCommandMessage(player, "home.set.custom", homeName);

        return true;

    }

}
