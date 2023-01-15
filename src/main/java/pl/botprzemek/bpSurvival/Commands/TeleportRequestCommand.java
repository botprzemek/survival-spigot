package pl.botprzemek.bpSurvival.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class TeleportRequestCommand implements CommandExecutor {

    private final BpSurvival instance;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public TeleportRequestCommand(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (pluginManager.getWaitingPlayers().containsKey(player.getUniqueId())) {

            messageManager.sendCommandMessage(player, "teleport.already");

            return false;

        }

        if (args.length == 0) {

            messageManager.sendCommandMessage(player, "tp.request.invalid");

            return false;

        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {

            messageManager.sendCommandMessage(player, "tp.request.offline");

            return false;

        }

        pluginManager.setTeleportingQueuePlayer(target, player);

        messageManager.sendCommandMessage(player, "tp.request.success", args[0]);

        messageManager.sendCommandMessage(target, "tp.request.request", player.getDisplayName());

        new BukkitRunnable() {

            public void run() { pluginManager.clearTeleportingQueuePlayer(target); }

        }.runTaskLaterAsynchronously(instance, 60 * 20);

        return true;

    }

}
