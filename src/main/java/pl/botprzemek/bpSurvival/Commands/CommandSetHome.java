package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.ArrayList;
import java.util.List;

public class CommandSetHome implements CommandExecutor, TabCompleter {

    private final ManagerPlugin managerPlugin;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    public CommandSetHome(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (player.getWorld().equals(managerPlugin.getSpawnLocation().getWorld())) {

            managerMessage.sendCommandMessage(player, "home.spawn");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "teleport.already");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Profile profile = managerProfile.getProfile(player);

        String homeName = (args.length == 0) ? "dom" : args[0];

        int maxHomes = 0;

        for (int i = 0; i <= 5; i++) {

            if (player.hasPermission("bpsurvival.homes." + i)) {

                maxHomes = i;

                break;

            }

        }

        if (maxHomes == 0) return setPlayerHome(player, profile, homeName);

        if (profile.getHomes().size() <= maxHomes) return setPlayerHome(player, profile, homeName);

        managerMessage.sendCommandMessage(player, "home.max");

        managerMessage.playPlayerSound(player, "error");

        return false;

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

            managerMessage.sendCommandMessage(player, "home.set.rewrite", homeName);

            managerMessage.playPlayerSound(player, "activate");

            return true;

        }

        profile.setHome(homeName, player.getLocation());

        managerMessage.sendCommandMessage(player, "home.set.custom", homeName);

        managerMessage.playPlayerSound(player, "activate");

        return true;
    }

}
