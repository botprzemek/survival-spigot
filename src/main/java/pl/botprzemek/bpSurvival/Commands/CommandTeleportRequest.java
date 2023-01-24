package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

public class CommandTeleportRequest implements CommandExecutor {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandTeleportRequest(ManagerSurvival managerSurvival) {

        instance = managerSurvival.getInstance();

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "teleport.already");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (args.length == 0) {

            managerMessage.sendCommandMessage(player, "tp.request.invalid");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {

            managerMessage.sendCommandMessage(player, "tp.request.offline");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (target.equals(player)) {

            managerMessage.sendCommandMessage(player, "tp.request.same");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        managerPlugin.setTeleportingQueuePlayer(target, player);

        managerMessage.sendCommandMessage(player, "tp.request.success", args[0]);

        managerMessage.sendCommandMessage(target, "tp.request.request", player.getDisplayName());

        managerMessage.playPlayerSound(player, "activate");

        new BukkitRunnable() {

            public void run() { managerPlugin.clearTeleportingQueuePlayer(target); }

        }.runTaskLaterAsynchronously(instance, 60 * 20);

        return true;

    }

}
