package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class MovementEvent implements Listener {

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public MovementEvent(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("bpsurvival.bypass")) return;

        if (Objects.equals(pluginManager.getSpawnLocation().getWorld(), player.getWorld())) {

            if (pluginManager.getWaitingPlayer(player) != -1) {

                pluginManager.clearWaitingPlayer(player);

                messageManager.sendCommandMessage(player, "teleport.failed");

                messageManager.playPlayerSound(player, "error");

            }

            if (player.getLocation().getY() > pluginManager.getLimit()) return;

            player.teleport(pluginManager.getSpawnLocation());

            messageManager.sendCommandMessage(player, "spawn.success");

        }

        if (pluginManager.getWaitingPlayer(player) == -1) return;

        pluginManager.clearWaitingPlayer(player);

        messageManager.sendCommandMessage(player, "teleport.failed");

        messageManager.playPlayerSound(player, "error");

    }

}
