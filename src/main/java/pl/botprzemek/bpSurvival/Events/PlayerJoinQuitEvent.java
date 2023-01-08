package pl.botprzemek.bpSurvival.Events;

import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitEvent implements Listener {

    private final ProfileManager profileManager;

    public PlayerJoinQuitEvent(SurvivalManager survivalManager) {

        this.profileManager = survivalManager.getProfileManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Profile profile = profileManager.getProfile(player);

        if (profile == null) profile = profileManager.createProfile(player);

        player.sendMessage("=== Your config ===");
        player.sendMessage("Level: " + profile.getLevel());
        player.sendMessage("Drop-To-Inv: " + profile.getSettings().isToInventory());
        player.sendMessage("Drop-Mined-Block: " + profile.getSettings().isMinedBlock());
        player.sendMessage("Multiplier: " + profile.getSettings().getMultiplier());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

    }

}
