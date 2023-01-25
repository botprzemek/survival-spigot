package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Kit;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommandKit implements CommandExecutor, TabCompleter {

    private final ManagerPlugin managerPlugin;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    public CommandKit(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {

            managerMessage.sendCommandMessage(player, "kits.invalid");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Profile profile = managerProfile.getProfile(player);

        Kit kit = managerPlugin.getKit(args[0].toLowerCase());

        String kitName = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        if (kit == null) {

            managerMessage.sendCommandMessage(player, "kits.empty");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (player.hasPermission("bpsurvival.kits-bypass")) {

            for (ItemStack item : kit.getItems()) player.getInventory().addItem(item);

            managerMessage.sendTitle(player, "commands.kits.success.title", kitName, kitName);

            managerMessage.sendCommandMessage(player, "kits.success.message", kitName);

            managerMessage.playPlayerSound(player, "success");

            return true;

        }

        if (!player.hasPermission("bpsurvival.kits." + args[0].toLowerCase())) {

            managerMessage.sendCommandMessage(player, "kits.deny", kitName);

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        long newTime = Instant.now().getEpochSecond();

        if (profile.getCooldowns().get("kits." + args[0].toLowerCase()) != null) {

            long oldTime = profile.getCooldowns().get("kits." + args[0].toLowerCase());

            if (oldTime + kit.getCooldown() >= newTime) {

                String newDate = new SimpleDateFormat("EEEE, dd MMMM, k:mm", new Locale("pl")).format(new Date((oldTime + kit.getCooldown()) * 1000));

                managerMessage.sendCommandMessage(player, "kits.cooldown", newDate);

                managerMessage.playPlayerSound(player, "error");

                return false;

            }

        }

        if (!managerPlugin.inventoryHaveSpace(player, kit.getItems().size())) {

            managerMessage.sendCommandMessage(player, "kits.full", kitName);

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        for (ItemStack item : kit.getItems()) player.getInventory().addItem(item);

        managerMessage.sendTitle(player, "commands.kits.success.title", kitName, kitName);

        managerMessage.sendCommandMessage(player, "kits.success.message", kitName);

        managerMessage.playPlayerSound(player, "success");

        profile.setCooldown("kits." + args[0].toLowerCase(), newTime);

        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return null;

        Profile profile = managerProfile.getProfile(player);

        List<Kit> kits = managerPlugin.getKits();

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
