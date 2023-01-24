package pl.botprzemek.bpSurvival.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.UUID;

public class EventChat implements Listener {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public EventChat(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission("bpsurvival.chat")) {

            event.setCancelled(true);

            managerMessage.sendEventMessage(player, "chat.failed", "");

            managerMessage.playPlayerSound(player, "error");

        }

        String originalMessage = event.getMessage();

        String format = managerMessage.getMessageString(player, "events.chat.success", originalMessage);

        event.setFormat(format);

        for (UUID playerUUID : managerPlugin.getStreamingPlayers()) {

            Player streamingPlayer = Bukkit.getPlayer(playerUUID);

            if (streamingPlayer != null && !streamingPlayer.equals(player)) event.getRecipients().remove(streamingPlayer);

        }

    }

}
