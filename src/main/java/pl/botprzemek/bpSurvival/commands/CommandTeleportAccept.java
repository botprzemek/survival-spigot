package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class CommandTeleportAccept implements CommandExecutor {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandTeleportAccept(ManagerSurvival managerSurvival) {

        instance = managerSurvival.getInstance();

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (!managerPlugin.getTeleportingQueuePlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "tp.accept.empty");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Player requestPlayer = Bukkit.getPlayer(managerPlugin.getTeleportingQueueRequestedPlayer(player));

        if (requestPlayer == null) {

            managerMessage.sendCommandMessage(player, "tp.accept.offline");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (player.hasPermission("bpsurvival.bypass")) {

            requestPlayer.teleport(player);

            managerMessage.sendCommandMessage(player, "tp.accept.success.requested", requestPlayer.getDisplayName());

            managerMessage.sendCommandMessage(requestPlayer, "tp.accept.success.target", player.getDisplayName());

            managerMessage.playPlayerSound(player, "activate");

            managerMessage.playPlayerSound(requestPlayer, "activate");

            return true;

        }

        managerPlugin.setWaitingPlayer(requestPlayer, 0);

        managerMessage.sendCommandMessage(player, "tp.accept.accept", requestPlayer.getDisplayName());

        managerMessage.sendCommandMessage(requestPlayer, "tp.accept.start", player.getDisplayName());

        managerMessage.playPlayerSound(player, "step");

        managerMessage.playPlayerSound(requestPlayer, "activate");

        new BukkitRunnable() {

            private int time = 1;

            public void run() {

                if (!managerPlugin.getWaitingPlayers().containsKey(requestPlayer.getUniqueId())) {

                    cancel();

                    return;

                }

                managerMessage.sendCommandMessage(requestPlayer, "tp.accept.time", String.valueOf(time));

                managerMessage.playPlayerSound(requestPlayer, "step");

                if (time == managerPlugin.getTimer()) {

                    requestPlayer.teleport(player);

                    managerPlugin.clearWaitingPlayer(requestPlayer);

                    managerMessage.sendCommandMessage(player, "tp.accept.success.requested", requestPlayer.getDisplayName());

                    managerMessage.sendCommandMessage(requestPlayer, "tp.accept.success.target", player.getDisplayName());

                    managerMessage.playPlayerSound(player, "activate");

                    managerMessage.playPlayerSound(requestPlayer, "activate");

                    cancel();

                    return;

                }

                time++;

                managerPlugin.setWaitingPlayer(requestPlayer, time);

            }

        }.runTaskTimer(instance, 20, 20);

        return true;

    }

}
