package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class EventChangeGamemode implements Listener {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public EventChangeGamemode(SurvivalPlugin survivalPlugin) {

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

    }

    @EventHandler
    public void onPlayerChangeGamemodeEvent(PlayerGameModeChangeEvent event) {

        if (!managerPlugin.getHiddenPlayers().contains(event.getPlayer().getUniqueId())) return;

        managerMessage.sendEventMessage(event.getPlayer(), "gamemode.change.hidden");

        event.setCancelled(true);

    }
}
