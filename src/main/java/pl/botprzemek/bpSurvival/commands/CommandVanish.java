package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

import java.util.UUID;

@Route(name = "vanish", aliases = "v")
@Permission("bpsurvival.player.command.spawn")
public class CommandVanish {
    @Inject BpSurvival bpSurvival;
    @Inject ManagerPlugin managerPlugin;
    @Inject ManagerMessage managerMessage;

    @Execute
    public boolean onVanish(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.isHiddenPlayer(player)) {
            player.setMetadata("vanished", new FixedMetadataValue(bpSurvival, false));
            managerPlugin.clearHiddenPlayer(player);
            player.setGameMode(GameMode.CREATIVE);

            for (Player target : Bukkit.getOnlinePlayers()) target.showPlayer(bpSurvival, player);
            for (UUID playerUUID : managerPlugin.getHiddenPlayers()) {
                Player target = Bukkit.getPlayer(playerUUID);
                if (target != null) target.showPlayer(bpSurvival, player);
            }

            managerMessage.sendAnnouncement(player, "connect.join", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size()));
            managerMessage.sendCommandMessage(player, "vanish.show");
            managerMessage.playPlayerSound(player, "activate");
            return true;
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!managerPlugin.getHiddenPlayers().contains(target.getUniqueId())) target.hidePlayer(bpSurvival, player);
        }

        player.setMetadata("vanished", new FixedMetadataValue(bpSurvival, true));
        player.setGameMode(GameMode.SPECTATOR);

        managerPlugin.setHiddenPlayer(player);
        managerMessage.sendAnnouncement(player, "connect.quit", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size()));
        managerMessage.sendCommandMessage(player, "vanish.hide");
        managerMessage.playPlayerSound(player, "activate");
        return true;
    }
}
