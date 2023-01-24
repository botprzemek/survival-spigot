package pl.botprzemek.bpSurvival.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.Objects;
import java.util.UUID;

public class EventJoinQuit implements Listener {

    private final BpSurvival instance;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    private final ManagerPlugin managerPlugin;

    public EventJoinQuit(ManagerSurvival managerSurvival) {

        instance = managerSurvival.getInstance();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

        managerPlugin = managerSurvival.getPluginManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(managerMessage.getMessageString(player, "events.connect.join", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size())));

        if (!player.hasPlayedBefore()) player.teleport(managerPlugin.getSpawnLocation());

        if (managerProfile.getProfile(player) == null) managerProfile.createProfile(player);

        for (UUID playerUUID : managerPlugin.getHiddenPlayers()) player.hidePlayer(instance, Objects.requireNonNull(Bukkit.getPlayer(playerUUID)));

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(managerMessage.getMessageString(player, "events.connect.quit"));

        managerPlugin.clearSleepingPlayer(player);

        managerPlugin.clearReplyPlayer(player);

    }

}
