package pl.botprzemek.bpSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class EventChangeGamemode implements Listener {

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public EventChangeGamemode(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @EventHandler
    public void onPlayerChangeGamemodeEvent(PlayerGameModeChangeEvent event) {

        if (!managerPlugin.getHiddenPlayers().contains(event.getPlayer().getUniqueId())) return;

        managerMessage.sendEventMessage(event.getPlayer(), "gamemode.change.hidden");

        event.setCancelled(true);

    }
}
