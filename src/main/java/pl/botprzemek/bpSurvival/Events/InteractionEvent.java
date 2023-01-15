package pl.botprzemek.bpSurvival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.time.Instant;
import java.util.Objects;

public class InteractionEvent implements Listener {

    private final ProfileManager profileManager;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public InteractionEvent(SurvivalManager survivalManager) {

        profileManager = survivalManager.getProfileManager();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @EventHandler
    public void onClickInteractionEvent(PlayerInteractEvent event) {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (!Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        double level = profileManager.getProfile(player).getLevel();

        if (!player.getInventory().getItemInMainHand().getType().toString().contains("PICKAXE")) return;

        if (level < 5) return;

        long newTime = Instant.now().getEpochSecond();

        if (pluginManager.getMinersBoostCooldown().get(player.getUniqueId()) != null) {

            long oldTime = pluginManager.getMinerBoostCooldown(player);

            if (oldTime + 60 >= newTime) {

                messageManager.sendEventMessage(player, "interact.boost.miner.cooldown", String.valueOf(60 - (newTime - oldTime)));

                return;

            }

            pluginManager.clearMinerBoostCooldown(player);

            activateMinerBoost(player, level, newTime);

            return;

        }

        activateMinerBoost(player, level, newTime);

    }

    private void activateMinerBoost(Player player, double level, Long newTime) {

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, (int) (level * 2), (int) Math.floor(level/5), false, false, false);

        player.addPotionEffect(potionEffect);

        pluginManager.setMinerBoostCooldown(player, newTime);

        messageManager.sendEventMessage(player, "interact.boost.miner.activate", String.valueOf((double) potionEffect.getDuration() / 20));

    }

}
