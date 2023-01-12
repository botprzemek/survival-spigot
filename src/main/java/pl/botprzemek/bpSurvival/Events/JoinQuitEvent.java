package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class JoinQuitEvent implements Listener {

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    private final PluginManager pluginManager;

    public JoinQuitEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

        pluginManager = survivalManager.getPluginManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(messageManager.getMessageString(player, "events.connect.join"));

        if (profileManager.getProfile(player) == null) profileManager.createProfile(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(messageManager.getMessageString(player, "events.connect.quit"));

        pluginManager.clearSleepingPlayer(player);

    }

}
