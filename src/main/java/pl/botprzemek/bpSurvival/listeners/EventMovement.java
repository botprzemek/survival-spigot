package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.Objects;

public class EventMovement implements Listener {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public EventMovement(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("bpsurvival.bypass")) return;

        if (Objects.equals(managerPlugin.getSpawnLocation().getWorld(), player.getWorld())) {

            if (managerPlugin.getWaitingPlayer(player) != -1) {

                managerPlugin.clearWaitingPlayer(player);

                managerMessage.sendCommandMessage(player, "teleport.failed");

                managerMessage.playPlayerSound(player, "error");

                return;

            }

            if (player.getLocation().getY() > managerPlugin.getLimit()) return;

            player.teleport(managerPlugin.getSpawnLocation());

            return;

        }

        if (managerPlugin.getWaitingPlayer(player) == -1) return;

        managerPlugin.clearWaitingPlayer(player);

        managerMessage.sendCommandMessage(player, "teleport.failed");

        managerMessage.playPlayerSound(player, "error");

    }

}