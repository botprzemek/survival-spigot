package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

import java.util.Objects;

public class EventBedSleep implements Listener {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public EventBedSleep(SurvivalPlugin survivalPlugin) {

        instance = survivalPlugin.getInstance();

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

    }

    @EventHandler
    public void onBedEnterEvent(PlayerBedEnterEvent event) {

        if (!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) return;

        Player player = event.getPlayer();

        if (!player.hasPermission("bpsurvival.sleep.skip")) return;

        if (managerPlugin.getHiddenPlayers().contains(player.getUniqueId())) return;

        managerPlugin.setSleepingPlayer(player);

        int percentage = (int) Math.floor(((double) managerPlugin.getSleepingPlayers().size() / (instance.getServer().getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size())) * 100);

        managerMessage.sendAnnouncement(player, "sleep.message", String.valueOf(percentage));

        if (percentage < 50) return;

        player.getWorld().setTime(0);

    }

    @EventHandler
    public void onBedLeaveEvent(PlayerBedLeaveEvent event) {

        Player player = event.getPlayer();

        if (player.getWorld().getTime() != 0) {

            managerPlugin.clearSleepingPlayer(player);

            return;

        }

        if (!managerPlugin.getSleepingPlayers().contains(player.getUniqueId())) return;

        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.setFoodLevel(20);

        managerMessage.sendTitle(player, "sleep.success");

        managerMessage.playPlayerSound(player, "step");

        managerPlugin.clearSleepingPlayer(player);

    }

}
