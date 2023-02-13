package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerProfile;

import java.util.List;
import java.util.UUID;

public class EventJoinQuit implements Listener {

    private final BpSurvival instance;
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;
    private final ManagerPlugin managerPlugin;


    public EventJoinQuit(SurvivalPlugin survivalPlugin) {
        instance = survivalPlugin.getInstance();
        managerProfile = survivalPlugin.getManagerProfile();
        managerMessage = survivalPlugin.getManagerMessage();
        managerPlugin = survivalPlugin.getManagerPlugin();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        List<UUID> hiddenPlayers = managerPlugin.getHiddenPlayers();

        if (!hiddenPlayers.contains(playerUUID)) event.setJoinMessage(managerMessage.getMessageString(player, "events.connect.join", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size())));

        if (!player.hasPlayedBefore()) player.teleport(managerPlugin.getSpawnLocation());
        if (managerProfile.getProfile(player) == null) managerProfile.createProfile(player);

        for (UUID targetUUID : hiddenPlayers) {
            Player target = Bukkit.getPlayer(targetUUID);
            if (target != null) player.hidePlayer(instance, target);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(managerMessage.getMessageString(player, "events.connect.quit"));

        managerPlugin.clearSleepingPlayer(player);
        managerPlugin.clearReplyPlayer(player);
    }

}
