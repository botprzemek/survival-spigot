package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.ArrayList;

public class CommandOpenChest implements CommandExecutor {

    private final ManagerMessage managerMessage;

    public CommandOpenChest(ManagerSurvival managerSurvival) {
        managerMessage = managerSurvival.getMessageManager();
    }

    public class RayTrace {
        Vector origin, direction;

        RayTrace(Vector origin, Vector direction) {
            this.origin = origin;
            this.direction = direction;
        }


        public Vector getPosition(double blocksAway) {
            return origin.clone().add(direction.clone().multiply(blocksAway));
        }

        public ArrayList<Vector> getAllTraverseLocations(double blocksAway, double acurracy) {
            ArrayList<Vector> positions = new ArrayList<>();

            for (double d = 0; d < blocksAway; d += acurracy) {
                positions.add(getPosition(d));
            }

            return positions;
        }
    }


    public void openInventory(Player player, Inventory inventory, Block target) {
        ItemStack[] contents = inventory.getContents();

        int size = (inventory.getSize() == 27) ? 27 : 54;

        Inventory preview = Bukkit.createInventory(null, size, target.getType().name());

        preview.setContents(contents);

        player.openInventory(preview);
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if ((args.length == 0)) {
            World world = player.getWorld();

            RayTrace rayTrace = new RayTrace(player.getEyeLocation().toVector(), player.getEyeLocation().getDirection());
            ArrayList<Vector> postions = rayTrace.getAllTraverseLocations(6, 0.1);


            for (Vector position : postions) {
                Location location = new Location(world, position.getX(), position.getY(), position.getZ());

                Block block = location.getBlock();
                Material blockType = block.getType();


                if (blockType == Material.CHEST) {
                    Chest chest = (Chest) block.getState();

                    openInventory(player, chest.getInventory(), block);

                    return true;
                } else if (blockType.name().contains("SHULKER_BOX")) {
                    ShulkerBox shulkerBox = (ShulkerBox) block.getState();

                    openInventory(player, shulkerBox.getInventory(), block);

                    return true;
                }
            }

            managerMessage.sendCommandMessage(player, "open-chest.none");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }



        return false;
    }
}
