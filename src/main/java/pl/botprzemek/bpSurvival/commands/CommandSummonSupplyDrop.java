package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.concurrent.ThreadLocalRandom;

public class CommandSummonSupplyDrop implements CommandExecutor {

    private final ManagerPlugin managerPlugin;

    private final ManagerProfile managerProfile;

    private final ManagerMessage managerMessage;

    public CommandSummonSupplyDrop(ManagerSurvival managerSurvival) {

        managerPlugin = managerSurvival.getPluginManager();

        managerProfile = managerSurvival.getProfileManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        int limit = 5000; //managerPlugin.getWorldBorder();

        Material blockType = Material.BARREL; // managerPlugin.getSupplyDropBlock();

        World world = Bukkit.getWorld("void"); // managerPlugin.getWorldName();

        if (world == null) return false;

        int multiplier = (random.nextBoolean()) ? -1 : 1;

        int x = multiplier * random.nextInt(0, limit);

        multiplier = (random.nextBoolean()) ? -1 : 1;

        int z = multiplier * random.nextInt(0, limit);

        int y = world.getHighestBlockYAt(x, z) + 1;

        Location dropLocation = new Location(world, x, y, z);

        dropLocation.getBlock().setType(blockType);

        sender.sendMessage("Postawiono blok na X: " + x + ", Y: " + y + "Z: " + z);

        if (sender instanceof Player player) player.teleport(dropLocation);

        Entity fallingBlock = world.spawnFallingBlock(dropLocation, dropLocation.getBlock().getBlockData());

        fallingBlock.setVelocity(new Vector(0, 2, 0));

        return true;

    }

}
