package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class ChatEvent implements Listener {

    private final MessageManager messageManager;

    public ChatEvent(SurvivalManager survivalManager) {

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission("bpsurvival.chat")) {

            event.setCancelled(true);

            messageManager.getMessageString(player, "events.chat.failed");

        }

        String originalMessage = event.getMessage();

        String format = messageManager.getMessageString(player, "events.chat.success", originalMessage);

        event.setFormat(format);

    }

}
