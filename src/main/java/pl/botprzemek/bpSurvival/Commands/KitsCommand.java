package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.time.Instant;
import java.util.List;

public class KitsCommand implements CommandExecutor {

    private final PluginManager pluginManager;

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    public KitsCommand(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) return false;

        Profile profile = profileManager.getProfile(player);

        List<ItemStack> kit = pluginManager.getKit(args[0].toLowerCase());

        String kitName = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        long newTime = Instant.now().getEpochSecond();

        if (profile.getCooldowns().get("kit." + args[0].toLowerCase()) != null) {

            long oldTime = profile.getCooldowns().get("kit." + args[0].toLowerCase());

            if (oldTime + 60 >= newTime) {

                messageManager.sendCommandMessage(player, "kit.cooldown", String.valueOf(60 - (newTime - oldTime)));

                return false;

            }

        }

        if (!pluginManager.inventoryHaveSpace(player, kit.size())) {

            messageManager.sendCommandMessage(player, "kit.full", kitName);

            return false;

        }

        for (ItemStack item : kit) player.getInventory().addItem(item);

        messageManager.sendTitle(player, "commands.kit.success.title", kitName, kitName);

        messageManager.sendCommandMessage(player, "kit.success.message", kitName);

        profile.setCooldown("kit." + args[0].toLowerCase(), newTime);

        return true;

    }

}
