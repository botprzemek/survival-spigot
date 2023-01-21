package pl.botprzemek.bpSurvival.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;
import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private final BpSurvival instance;

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    private final PluginManager pluginManager;

    public JoinQuitEvent(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

        pluginManager = survivalManager.getPluginManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(messageManager.getMessageString(player, "events.connect.join", String.valueOf(Bukkit.getOnlinePlayers().size() - pluginManager.getHiddenPlayers().size())));

        if (profileManager.getProfile(player) == null) profileManager.createProfile(player);

        for (UUID playerUUID : pluginManager.getHiddenPlayers()) player.hidePlayer(instance, Objects.requireNonNull(Bukkit.getPlayer(playerUUID)));

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(messageManager.getMessageString(player, "events.connect.quit"));

        pluginManager.clearSleepingPlayer(player);

        pluginManager.clearReplyPlayer(player);

    }

}
