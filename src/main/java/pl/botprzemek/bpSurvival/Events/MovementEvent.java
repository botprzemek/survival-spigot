package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

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

        if (pluginManager.getWaitingPlayer(player) == -1) return;

        messageManager.sendCommandMessage(player, "spawn.failed");

        pluginManager.clearWaitingPlayer(player);

    }

}
