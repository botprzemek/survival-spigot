package pl.botprzemek.bpSurvival.commands;

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

import java.util.List;

public class CommandRemoveHome implements CommandExecutor, TabCompleter {

    private final ManagerPlugin managerPlugin;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    public CommandRemoveHome(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        Profile profile = managerProfile.getProfile(player);

        String homeName = (args.length == 0) ? "dom" : args[0];

        if (profile.getHomes().containsKey(homeName)) {

            if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

                managerMessage.sendCommandMessage(player, "home.remove.this");

                managerMessage.playPlayerSound(player, "error");

                return false;

            }

            managerMessage.sendCommandMessage(player, "home.remove.success");

            managerMessage.playPlayerSound(player, "activate");

            profile.removeHome(homeName);

            return true;

        }

        managerMessage.sendCommandMessage(player, "home.remove.error");

        managerMessage.playPlayerSound(player, "error");

        return false;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return null;

        Profile profile = managerProfile.getProfile(player);

        return profile.getHomes().keySet().stream().toList();

    }

}