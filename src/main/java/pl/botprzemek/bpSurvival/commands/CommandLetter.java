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
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerProfile;
import pl.botprzemek.bpSurvival.survival.utils.Profile;

public class CommandLetter implements CommandExecutor {
    private final ManagerPlugin managerPlugin;
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;

    public CommandLetter(SurvivalPlugin survivalPlugin) {
        managerPlugin = survivalPlugin.getManagerPlugin();
        managerProfile = survivalPlugin.getManagerProfile();
        managerMessage = survivalPlugin.getManagerMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;

        PlayerInventory inventory = player.getInventory();

        if (!inventory.getItemInMainHand().isSimilar(OraxenItems.getItemById("love_letter").build())) return false;

        Profile profile = managerProfile.getProfile(player);
        Player target = Bukkit.getPlayer(args[0]);
        ItemStack item = getCustomItem(player.getDisplayName());

        if (target == null) return false;

        if (profile.getLetters().containsKey(target.getUniqueId())) return false;

        target.getInventory().addItem(item);

        inventory.setItem(inventory.getHeldItemSlot(), new ItemStack(Material.AIR));

        profile.setLetter(target, true);

        Bukkit.getLogger().info(profile.getLetters().toString());

        return true;
    }

    private ItemStack getCustomItem(String playerName) {
        ItemStack item = OraxenItems.getItemById("love_letter").build();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        meta.setDisplayName("§rWalentynka od " + playerName);
        item.setItemMeta(meta);

        return item;
    }
}
