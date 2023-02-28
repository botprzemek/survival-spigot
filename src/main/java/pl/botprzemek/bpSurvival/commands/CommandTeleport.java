package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;


@Route(name = "teleport", aliases = "tp")
@Permission("bpsurvival.admin.command.teleport")
public class CommandTeleport implements CommandExecutor {
    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {
            managerMessage.sendCommandMessage(player, "teleport.already");
            managerMessage.playPlayerSound(player, "error");
            return false;
        }

        if (args.length == 0) {
            managerMessage.sendCommandMessage(player, "tp.player.invalid");
            managerMessage.playPlayerSound(player, "error");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            managerMessage.sendCommandMessage(player, "tp.player.offline");
            managerMessage.playPlayerSound(player, "error");
            return false;
        }

        if (target.equals(player)) {
            managerMessage.sendCommandMessage(player, "tp.player.same");
            managerMessage.playPlayerSound(player, "error");
            return false;
        }

        player.teleport(target);

        managerMessage.sendCommandMessage(player, "tp.player.success", args[0]);
        managerMessage.playPlayerSound(player, "activate");
        return true;
    }
}
