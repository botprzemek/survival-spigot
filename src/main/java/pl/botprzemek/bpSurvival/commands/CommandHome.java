package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.utils.Profile;
import pl.botprzemek.bpSurvival.survival.managers.ManagerProfile;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

import java.util.List;

public class CommandHome implements CommandExecutor, TabCompleter {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    private final ManagerProfile managerProfile;

    public CommandHome(SurvivalPlugin survivalPlugin) {

        instance = survivalPlugin.getInstance();

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

        managerProfile = survivalPlugin.getManagerProfile();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        Profile profile = managerProfile.getProfile(player);

        String homeName = (args.length == 0) ? "dom" : args[0];

        if (profile.getHomes().get(homeName) == null) {

            managerMessage.sendCommandMessage(player, "home.teleport.empty", homeName);

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Location location = profile.getHomes().get(homeName);

        if (player.hasPermission("bpsurvival.bypass")) {

            player.teleport(location);

            managerMessage.sendCommandMessage(player, "home.teleport.success", homeName);

            managerMessage.playPlayerSound(player, "activate");

            return true;

        }

        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "teleport.already");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        managerPlugin.setWaitingPlayer(player, 0);

        managerMessage.sendCommandMessage(player, "home.teleport.start", homeName);

        managerMessage.playPlayerSound(player, "activate");

        new BukkitRunnable() {

            private int time = 1;

            public void run() {

                if (!managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

                    cancel();

                    return;

                }

                managerMessage.sendCommandMessage(player, "home.teleport.time", String.valueOf(time));

                managerMessage.playPlayerSound(player, "step");

                if (time == managerPlugin.getTimer()) {

                    player.teleport(location);

                    managerPlugin.clearWaitingPlayer(player);

                    managerMessage.sendCommandMessage(player, "home.teleport.success", homeName);

                    managerMessage.playPlayerSound(player, "activate");

                    cancel();

                    return;

                }

                time++;

                managerPlugin.setWaitingPlayer(player, time);

            }

        }.runTaskTimer(instance, 20, 20);

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
