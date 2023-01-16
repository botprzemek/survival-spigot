package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.DropManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.time.Instant;
import java.util.Objects;

public class InteractionEvent implements Listener {

    private final ProfileManager profileManager;

    private final DropManager dropManager;

    private final MessageManager messageManager;

    public InteractionEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        dropManager = survivalManager.getDropManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onClickInteractionEvent(PlayerInteractEvent event) {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (!Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;

        if (!dropManager.getBlocks().contains(Objects.requireNonNull(event.getClickedBlock()).getType())) return;

        Player player = event.getPlayer();

        Profile profile = profileManager.getProfile(player);

        double level = profile.getLevel();

        if (!player.getInventory().getItemInMainHand().getType().toString().contains("PICKAXE")) return;

        if (level < 5) return;

        long newTime = Instant.now().getEpochSecond();

        if (profile.getCooldowns().get("boosts.mining") != null) {

            long oldTime = profile.getCooldowns().get("boosts.mining");

            if (oldTime + 60 >= newTime) {

                messageManager.sendEventMessage(player, "interact.boosts.mining.cooldown", String.valueOf(60 - (newTime - oldTime)));

                messageManager.playPlayerSound(player, "error");

                return;

            }

            profile.clearCooldown("boosts.mining");

            activateMinerBoost(player, level, newTime);

            return;

        }

        activateMinerBoost(player, level, newTime);

    }

    private void activateMinerBoost(Player player, double level, Long newTime) {

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, (int) (level * 2), (int) Math.floor(level/5), false, false, false);

        player.addPotionEffect(potionEffect);

        profileManager.getProfile(player).setCooldown("boosts.mining", newTime);

        messageManager.sendEventMessage(player, "interact.boosts.mining.activate", String.valueOf((double) potionEffect.getDuration() / 20));

        messageManager.playPlayerSound(player, "success");

    }

}
