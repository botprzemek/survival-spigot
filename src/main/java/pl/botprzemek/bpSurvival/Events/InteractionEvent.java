package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.util.Objects;

public class InteractionEvent implements Listener {

    private final ProfileManager profileManager;

    public InteractionEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

    }

    @EventHandler
    public void onClickInteractionEvent(PlayerInteractEvent event) {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (!Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        double level = profileManager.getProfile(player).getLevel();

        if (!player.getInventory().getItemInMainHand().getType().toString().contains("PICKAXE")) return;

        if (level < 5) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (int) (level * 2), (int) Math.floor(level/5), false, false, false));

        for (PotionEffect potionEffect : player.getActivePotionEffects()) player.sendMessage("Aktywowano efekt: " + potionEffect.getType().getName() + ", Na: " + potionEffect.getDuration() + "/20, Lvl: " + potionEffect.getAmplifier());

    }

}
