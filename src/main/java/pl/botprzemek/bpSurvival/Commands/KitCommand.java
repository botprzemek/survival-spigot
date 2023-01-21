package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Kit;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ProfileManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KitCommand implements CommandExecutor, TabCompleter {

    private final PluginManager pluginManager;

    private final ProfileManager profileManager;

    private final MessageManager messageManager;

    public KitCommand(SurvivalManager survivalManager) {

        pluginManager = survivalManager.getPluginManager();

        profileManager = survivalManager.getProfileManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {

            messageManager.sendCommandMessage(player, "kits.invalid");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        Profile profile = profileManager.getProfile(player);

        Kit kit = pluginManager.getKit(args[0].toLowerCase());

        String kitName = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        if (kit == null) {

            messageManager.sendCommandMessage(player, "kits.empty");

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        if (!player.hasPermission("bpsurvival.kits." + args[0].toLowerCase())) {

            messageManager.sendCommandMessage(player, "kits.deny", kitName);

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        long newTime = Instant.now().getEpochSecond();

        if (profile.getCooldowns().get("kits." + args[0].toLowerCase()) != null) {

            long oldTime = profile.getCooldowns().get("kits." + args[0].toLowerCase());

            if (oldTime + kit.getCooldown() >= newTime) {

                String newDate = new SimpleDateFormat("EEEE, dd MMMM, k:mm", new Locale("pl")).format(new Date((oldTime + kit.getCooldown()) * 1000));

                messageManager.sendCommandMessage(player, "kits.cooldown", newDate);

                messageManager.playPlayerSound(player, "error");

                return false;

            }

        }

        if (!pluginManager.inventoryHaveSpace(player, kit.getItems().size())) {

            messageManager.sendCommandMessage(player, "kits.full", kitName);

            messageManager.playPlayerSound(player, "error");

            return false;

        }

        for (ItemStack item : kit.getItems()) player.getInventory().addItem(item);

        messageManager.sendTitle(player, "commands.kits.success.title", kitName, kitName);

        messageManager.sendCommandMessage(player, "kits.success.message", kitName);

        messageManager.playPlayerSound(player, "success");

        profile.setCooldown("kits." + args[0].toLowerCase(), newTime);

        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return null;

        Profile profile = profileManager.getProfile(player);

        List<Kit> kits = pluginManager.getKits();

        List<String> kitNames = new ArrayList<>();

        for (Kit kit : kits) {

            String kitName = kit.getName().substring(0, 1).toUpperCase() + kit.getName().substring(1).toLowerCase();

            Long oldTime = profile.getCooldowns().get("kit." + kit.getName());

            if (player.hasPermission("bpsurvival.kits." + kit.getName())) {

                if (oldTime == null) kitNames.add(kitName);

            }

        }

        return kitNames;

    }

}
