package pl.botprzemek.bpSurvival.Commands;

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
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.List;

public class HomeCommand implements CommandExecutor, TabCompleter {

    private final BpSurvival instance;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    private final ProfileManager profileManager;

    public HomeCommand(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

        profileManager = survivalManager.getProfileManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        String homeName = (args.length == 0) ? "dom" : args[0];

        Profile profile = profileManager.getProfile(player);

        if (profile.getHomes().get(homeName) == null) {

            messageManager.sendCommandMessage(player, "home.teleport.empty", homeName);

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        Location location = profile.getHomes().get(homeName);

        if (pluginManager.getWaitingPlayers().containsKey(player.getUniqueId())) {

            messageManager.sendCommandMessage(player, "teleport.already");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        pluginManager.setWaitingPlayer(player, 0);

        messageManager.sendCommandMessage(player, "home.teleport.start", homeName);

        messageManager.playPlayerSound(player, "activate");

        new BukkitRunnable() {

            private int time = 1;

            public void run() {

                if (!pluginManager.getWaitingPlayers().containsKey(player.getUniqueId())) {

                    cancel();

                    return;

                }

                messageManager.sendCommandMessage(player, "home.teleport.time", String.valueOf(time));

                messageManager.playPlayerSound(player, "step");

                if (time == pluginManager.getTimer()) {

                    player.teleport(location);

                    pluginManager.clearWaitingPlayer(player);

                    messageManager.sendCommandMessage(player, "home.teleport.success", homeName);

                    messageManager.playPlayerSound(player, "activate");

                    cancel();

                    return;

                }

                time++;

                pluginManager.setWaitingPlayer(player, time);

            }

        }.runTaskTimer(instance, 20, 20);

        return false;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return null;

        Profile profile = profileManager.getProfile(player);

        return profile.getHomes().keySet().stream().toList();

    }

}
