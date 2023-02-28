package pl.botprzemek.bpSurvival.commands;

import com.google.common.collect.Lists;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Route(name = "lottery", aliases = "losowanie")
@Permission("bpsurvival.admin.command.lottery")
public class CommandLottery {
    @Inject BpSurvival bpSurvival;
    @Inject ManagerPlugin managerPlugin;
    @Inject ManagerMessage managerMessage;

    @Execute
    public void lottery() {
        Bukkit.getScheduler().runTaskTimer(bpSurvival, () -> {
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
            }.runTaskTimer(bpSurvival, 20, 20);
        }, 0, managerPlugin.getEventTimer());
    }
}
