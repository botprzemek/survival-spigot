package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.Collection;

public class CommandOpenInv implements CommandExecutor {

    private final ManagerMessage managerMessage;

    public CommandOpenInv(ManagerSurvival managerSurvival) {
        managerMessage = managerSurvival.getMessageManager();
    }

    public boolean isPlayer(Entity entity) {
        return entity.getType() == EntityType.PLAYER;
    }

    public void openInv(Player player, Player target) {
        if (player == target) {
            managerMessage.sendCommandMessage(player, "open-inv.same");

            managerMessage.playPlayerSound(player, "error");

            return;
        }

        Inventory targetInventory = target.getInventory();
        ItemStack[] contents = targetInventory.getContents();

        Inventory preview = Bukkit.createInventory(target, InventoryType.PLAYER, target.getDisplayName());
        preview.setContents(contents);


        player.openInventory(preview);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if ((args.length == 0)) {
            World world = player.getWorld();

            Collection<Entity> entities = world.getNearbyEntities(player.getLocation(), 10, 4, 10);
            Player closestPlayer = null;
            double distance = 100.0;


            for (Entity entity : entities) {
                if (isPlayer(entity)) {
                    Location entityLocation = entity.getLocation();

                    double currentDistance = entityLocation.distance(player.getLocation());

                    if(currentDistance < distance ) {
                        closestPlayer = (Player) entity;
                    }
                }
            }

            openInv(player, closestPlayer);

            return false;
        }

        String playerName = args[0];

        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            managerMessage.sendCommandMessage(player, "open-inv.offline");

            managerMessage.playPlayerSound(player, "error");

            return false;
        }

        openInv(player, target);

        return false;
    }
}
