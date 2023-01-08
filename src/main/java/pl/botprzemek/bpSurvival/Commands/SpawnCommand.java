package pl.botprzemek.bpSurvival.Commands;

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

public class SpawnCommand implements CommandExecutor {

    private final BpSurvival instance;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    public SpawnCommand(SurvivalManager survivalManager) {

        instance = survivalManager.getInstance();

        pluginManager = survivalManager.getPluginManager();

        messageManager = survivalManager.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        pluginManager.setWaitingPlayer(player, 0);

        messageManager.sendMessage(player, "spawn.start");

        new BukkitRunnable() {

            private int time = 1;

            public void run() {

                if (!pluginManager.getWaitingPlayers().containsKey(player.getUniqueId())) {

                    cancel();

                    return;

                }

                messageManager.sendMessage(player, "spawn.time", String.valueOf(time));

                if (time == pluginManager.getTimer()) {

                    player.teleport(pluginManager.getSpawnLocation());

                    pluginManager.clearWaitingPlayer(player);

                    messageManager.sendMessage(player, "spawn.success");

                    cancel();

                    return;

                }

                time++;

                pluginManager.setWaitingPlayer(player, time);

            }

        }.runTaskTimer(instance, 20, 20);

        return false;

    }
}
