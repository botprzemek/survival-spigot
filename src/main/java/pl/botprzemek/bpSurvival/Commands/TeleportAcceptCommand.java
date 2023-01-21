package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class TeleportAcceptCommand implements CommandExecutor {

    private final BpSurvival instance;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public TeleportAcceptCommand(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (!pluginManager.getTeleportingQueuePlayers().containsKey(player.getUniqueId())) {

            messageManager.sendCommandMessage(player, "tp.accept.empty");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        Player requestPlayer = Bukkit.getPlayer(pluginManager.getTeleportingQueueRequestedPlayer(player));

        if (requestPlayer == null) {

            messageManager.sendCommandMessage(player, "tp.accept.offline");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        pluginManager.setWaitingPlayer(requestPlayer, 0);

        messageManager.sendCommandMessage(player, "tp.accept.accept", requestPlayer.getDisplayName());

        messageManager.sendCommandMessage(requestPlayer, "tp.accept.start", player.getDisplayName());

        messageManager.playPlayerSound(player, "step");

        messageManager.playPlayerSound(requestPlayer, "activate");

        new BukkitRunnable() {

            private int time = 1;

            public void run() {

                if (!pluginManager.getWaitingPlayers().containsKey(requestPlayer.getUniqueId())) {

                    cancel();

                    return;

                }

                messageManager.sendCommandMessage(requestPlayer, "tp.accept.time", String.valueOf(time));

                messageManager.playPlayerSound(requestPlayer, "step");

                if (time == pluginManager.getTimer()) {

                    requestPlayer.teleport(player);

                    pluginManager.clearWaitingPlayer(requestPlayer);

                    messageManager.sendCommandMessage(player, "tp.accept.success.requested", requestPlayer.getDisplayName());

                    messageManager.sendCommandMessage(requestPlayer, "tp.accept.success.target", player.getDisplayName());

                    messageManager.playPlayerSound(player, "activate");

                    messageManager.playPlayerSound(requestPlayer, "activate");

                    cancel();

                    return;

                }

                time++;

                pluginManager.setWaitingPlayer(requestPlayer, time);

            }

        }.runTaskTimer(instance, 20, 20);

        return true;

    }

}
