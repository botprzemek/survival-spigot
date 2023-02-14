package pl.botprzemek.bpSurvival.commands;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerProfile;
import pl.botprzemek.bpSurvival.survival.utils.Profile;

public class CommandLetter implements CommandExecutor {
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;

    public CommandLetter(SurvivalPlugin survivalPlugin) {
        managerProfile = survivalPlugin.getManagerProfile();
        managerMessage = survivalPlugin.getManagerMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            managerMessage.sendCommandMessage(player, "letter.invalid");
            return false;
        }

        PlayerInventory inventory = player.getInventory();

        if (!inventory.getItemInMainHand().isSimilar(OraxenItems.getItemById("love_letter").build())) {
            managerMessage.sendCommandMessage(player, "letter.item");
            return false;
        }

        Profile profile = managerProfile.getProfile(player);
        Player target = Bukkit.getPlayer(args[0]);
        ItemStack item = getLetterItem(player.getDisplayName());

        if (target == null) {
            managerMessage.sendCommandMessage(player, "letter.offline");
            return false;
        }

        if (target == player) {
            managerMessage.sendCommandMessage(player, "letter.same");
            return false;
        }

        if (profile.getLetters().containsKey(target.getUniqueId())) {
            managerMessage.sendCommandMessage(player, "letter.limit");
            return false;
        }

        target.getInventory().addItem(item);
        inventory.setItem(inventory.getHeldItemSlot(), new ItemStack(Material.AIR));
        profile.setLetter(target, true);

        managerMessage.sendMessageToReceiver(player, target, args, 1);

        return true;
    }

    private ItemStack getLetterItem(String playerName) {
        ItemStack item = OraxenItems.getItemById("love_letter").build();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        meta.setDisplayName("§r§fWalentynka od " + playerName);
        item.setItemMeta(meta);

        return item;
    }
}
