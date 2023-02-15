package pl.botprzemek.bpSurvival.commands;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandAdminLetter implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) return false;

        Inventory inventory = player.getInventory();
        ItemStack item = OraxenItems.getItemById("love_letter").build();
        int amount = Integer.parseInt(args[1]);

        if (amount < 0) inventory.addItem(item);

        item.setAmount(Integer.parseInt(args[1]));
        inventory.addItem(item);

        return true;
    }
}
