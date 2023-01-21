package pl.botprzemek.bpSurvival.Events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class BedSleepEvent implements Listener {

    private final BpSurvival instance;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public BedSleepEvent(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onBedEnterEvent(PlayerBedEnterEvent event) {

        if (!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) return;

        Player player = event.getPlayer();

        if (!player.hasPermission("bpsurvival.sleep.skip")) return;

        if (pluginManager.getHiddenPlayers().contains(player.getUniqueId())) return;

        pluginManager.setSleepingPlayer(player);

        int percentage = (int) Math.floor(((double) pluginManager.getSleepingPlayers().size() / (instance.getServer().getOnlinePlayers().size() - pluginManager.getHiddenPlayers().size())) * 100);

        messageManager.sendAnnouncement(player, "sleep.message", String.valueOf(percentage));

        if (percentage < 50) return;

        player.getWorld().setTime(0);

    }

    @EventHandler
    public void onBedLeaveEvent(PlayerBedLeaveEvent event) {

        Player player = event.getPlayer();

        if (player.getWorld().getTime() != 0) {

            pluginManager.clearSleepingPlayer(player);

            return;

        }

        if (!pluginManager.getSleepingPlayers().contains(player.getUniqueId())) return;

        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.setFoodLevel(20);

        messageManager.sendTitle(player, "sleep.success");

        messageManager.playPlayerSound(player, "step");

        pluginManager.clearSleepingPlayer(player);

    }

}
