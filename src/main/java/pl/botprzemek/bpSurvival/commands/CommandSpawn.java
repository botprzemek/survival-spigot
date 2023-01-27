package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

public class CommandSpawn implements CommandExecutor {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandSpawn(SurvivalPlugin survivalPlugin) {

        instance = survivalPlugin.getInstance();

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.getWaitingPlayers().containsKey(player.getUniqueId())) {

            managerMessage.sendCommandMessage(player, "teleport.already");

            managerMessage.playPlayerSound(player, "error");

            return false;

        }

        if (player.hasPermission("bpsurvival.bypass")) {

            player.teleport(managerPlugin.getSpawnLocation());

            managerPlugin.clearWaitingPlayer(player);

            managerMessage.sendCommandMessage(player, "spawn.success");

            managerMessage.playPlayerSound(player, "activate");

            return true;

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

        return false;

    }

}
