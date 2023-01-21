package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand implements CommandExecutor, TabCompleter {

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

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        Profile profile = profileManager.getProfile(player);

        String homeName = (args.length == 0) ? "dom" : args[0];

        int maxHomes = 0;

        for (int i = 0; i <= 5; i++) {

            if (player.hasPermission("bpsurvival.homes." + i)) {

                maxHomes = i;

                break;

            }

        }

        if (maxHomes == 0) {

            return setPlayerHome(player, profile, homeName);

        }

        if (profile.getHomes().size() >= maxHomes) {

            messageManager.sendCommandMessage(player, "home.max");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        return setPlayerHome(player, profile, homeName);

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> suggestion = new ArrayList<>();

        suggestion.add("nazwa");

        return suggestion;

    }

    private boolean setPlayerHome(Player player, Profile profile, String homeName) {
        if (profile.getHomes().containsKey(homeName)) {

            profile.setHome(homeName, player.getLocation());

            messageManager.sendCommandMessage(player, "home.set.rewrite", homeName);

            messageManager.playPlayerSound(player, "activate");

            return true;

        }

        profile.setHome(homeName, player.getLocation());

        messageManager.sendCommandMessage(player, "home.set.custom", homeName);

        messageManager.playPlayerSound(player, "activate");

        return true;
    }

}
