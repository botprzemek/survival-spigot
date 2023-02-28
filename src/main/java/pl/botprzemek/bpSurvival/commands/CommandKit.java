package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.argument.Args;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerProfile;
import pl.botprzemek.bpSurvival.survival.utils.Kit;
import pl.botprzemek.bpSurvival.survival.utils.Profile;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

@Route(name = "kit", aliases = "zestaw")
@Permission("bpsurvival.player.command.kit")
public class CommandKit {
    private final ManagerPlugin managerPlugin;
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;

    @Execute
    public void onKit(Player player, @Args String kitName) {
        if (kitName == null) {
            managerMessage.sendCommandMessage(player, "kits.invalid");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        Profile profile = managerProfile.getProfile(player);
        Kit kit = managerPlugin.getKit(kitName);
        String kitTitle = kitName.substring(0, 1).toUpperCase() + kitName.substring(1).toLowerCase();

        if (kit == null) {
            managerMessage.sendCommandMessage(player, "kits.empty");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        if (player.hasPermission("bpsurvival.player.kits-bypass")) {
            for (ItemStack item : kit.getItems()) player.getInventory().addItem(item);
            managerMessage.sendTitle(player, "commands.kits.success.title", kitName, kitName);
            managerMessage.sendCommandMessage(player, "kits.success.message", kitName);
            managerMessage.playPlayerSound(player, "success");
            return;
        }

        if (!player.hasPermission("bpsurvival.kits." + kitName.toLowerCase())) {
            managerMessage.sendCommandMessage(player, "kits.deny", kitName);
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        long newTime = Instant.now().getEpochSecond();

        if (profile.getCooldowns().get("kits." + kitName.toLowerCase()) != null) {
            long oldTime = profile.getCooldowns().get("kits." + kitName.toLowerCase());

            if (oldTime + kit.getCooldown() >= newTime) {
                String newDate = new SimpleDateFormat("EEEE, dd MMMM, k:mm", new Locale("pl")).format(new Date((oldTime + kit.getCooldown()) * 1000));
                managerMessage.sendCommandMessage(player, "kits.cooldown", newDate);
                managerMessage.playPlayerSound(player, "error");
                return;
            }
        }

        if (!managerPlugin.inventoryHaveSpace(player, kit.getItems().size())) {
            managerMessage.sendCommandMessage(player, "kits.full", kitName);
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        for (ItemStack item : kit.getItems()) player.getInventory().addItem(item);

        managerMessage.sendTitle(player, "commands.kits.success.title", kitName, kitName);
        managerMessage.sendCommandMessage(player, "kits.success.message", kitName);
        managerMessage.playPlayerSound(player, "success");

        profile.setCooldown("kits." + kitName.toLowerCase(), newTime);
    }
}
