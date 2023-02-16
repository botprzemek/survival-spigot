package pl.botprzemek.bpSurvival.commands;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandLottery implements CommandExecutor {
    private final BpSurvival instance;
    private final ManagerPlugin managerPlugin;
    private final ManagerMessage managerMessage;

    public CommandLottery(SurvivalPlugin survivalPlugin) {
        instance = survivalPlugin.getInstance();
        managerPlugin = survivalPlugin.getManagerPlugin();
        managerMessage = survivalPlugin.getManagerMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());

            if (players.size() < 2) {
                managerMessage.sendAnnouncement("broadcast.lottery.empty", String.valueOf(players.size()));
                return;
            }

            new BukkitRunnable() {
                private int time = 3;
                public void run() {
                    if (time == 0) {
                        Player player = players.get(ThreadLocalRandom.current().nextInt(players.size()));

                        managerMessage.sendAnnouncement(player, "broadcast.lottery.success", player.getDisplayName());
                        managerMessage.playPlayerSound("success");

                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crates key give " + player.getDisplayName() + " love_key 1");
                        cancel();
                        return;
                    }
                    managerMessage.sendAnnouncement("broadcast.lottery.step", String.valueOf(time));
                    managerMessage.playPlayerSound("step");
                    time--;
                }
            }.runTaskTimer(instance, 20, 20);
        }, 0, managerPlugin.getEventTimer());

        return true;
    }
}
