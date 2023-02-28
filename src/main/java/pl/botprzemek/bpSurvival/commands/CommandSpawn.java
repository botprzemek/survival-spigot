package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

@Route(name = "spawn")
@Permission("bpsurvival.player.command.spawn")
public class CommandSpawn implements CommandExecutor {
    private final BpSurvival instance;
    private final ManagerPlugin managerPlugin;
    private final ManagerMessage managerMessage;

    @Execute
    public void onSpawn(Player player) {
        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {
            managerMessage.sendCommandMessage(player, "teleport.already");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        if (player.hasPermission("bpsurvival.bypass")) {
            player.teleport(managerPlugin.getSpawnLocation());
            managerPlugin.clearWaitingPlayer(player);
            managerMessage.sendCommandMessage(player, "spawn.success");
            managerMessage.playPlayerSound(player, "activate");
            return;
        }

        managerPlugin.setWaitingPlayer(player, 0);
        managerMessage.sendCommandMessage(player, "spawn.start");
        managerMessage.playPlayerSound(player, "activate");

        new BukkitRunnable() {
            private int time = 1;
            public void run() {

                if (!managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {
                    cancel();
                    return;
                }

                managerMessage.sendCommandMessage(player, "spawn.time", String.valueOf(time));
                managerMessage.playPlayerSound(player, "step");

                if (time == managerPlugin.getTimer()) {
                    player.teleport(managerPlugin.getSpawnLocation());

                    managerPlugin.clearWaitingPlayer(player);
                    managerMessage.sendCommandMessage(player, "spawn.success");
                    managerMessage.playPlayerSound(player, "activate");

                    cancel();
                    return;
                }
                time++;
                managerPlugin.setWaitingPlayer(player, time);
            }
        }.runTaskTimer(instance, 20, 20);
    }
}
