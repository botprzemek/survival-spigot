package pl.botprzemek.bpSurvival.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.UUID;

public class ChatEvent implements Listener {

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public ChatEvent(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission("bpsurvival.chat")) {

            event.setCancelled(true);

            messageManager.sendEventMessage(player, "chat.failed", "");

            messageManager.playPlayerSound(player, "error");

        }

        String originalMessage = event.getMessage();

        String format = messageManager.getMessageString(player, "events.chat.success", originalMessage);

        event.setFormat(format);

        for (UUID playerUUID : pluginManager.getStreamingPlayers()) {

            Player streamingPlayer = Bukkit.getPlayer(playerUUID);

            if (streamingPlayer != null && !streamingPlayer.equals(player)) event.getRecipients().remove(streamingPlayer);

        }

    }

}
