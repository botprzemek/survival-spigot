package pl.botprzemek.bpSurvival.Events;

import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitEvent implements Listener {

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    public PlayerJoinQuitEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(messageManager.getMessageString(player, "connect.join"));

        if (profileManager.getProfile(player) == null) profileManager.createProfile(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(messageManager.getMessageString(player, "connect.quit"));

    }

}
