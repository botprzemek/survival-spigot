package pl.botprzemek.bpSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

import java.util.UUID;

public class CommandVanish implements CommandExecutor {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandVanish(SurvivalPlugin survivalPlugin) {

        instance = survivalPlugin.getInstance();

        managerPlugin = survivalPlugin.getManagerPlugin();

        managerMessage = survivalPlugin.getManagerMessage();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.isHiddenPlayer(player)) {

            player.setMetadata("vanished", new FixedMetadataValue(instance, false));

            managerPlugin.clearHiddenPlayer(player);

            player.setGameMode(GameMode.CREATIVE);

            for (Player target : Bukkit.getOnlinePlayers()) target.showPlayer(instance, player);

            for (UUID playerUUID : managerPlugin.getHiddenPlayers()) {
                Player target = Bukkit.getPlayer(playerUUID);
                if (target != null) target.showPlayer(instance, player);
            }

            managerMessage.sendAnnouncement(player, "connect.join", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size()));

            managerMessage.sendCommandMessage(player, "vanish.show");

            managerMessage.playPlayerSound(player, "activate");

            return true;

        }

        for (Player target : Bukkit.getOnlinePlayers()) {

            if (!managerPlugin.getHiddenPlayers().contains(target.getUniqueId())) target.hidePlayer(instance, player);

        }

        player.setMetadata("vanished", new FixedMetadataValue(instance, true));

        player.setGameMode(GameMode.SPECTATOR);

        managerPlugin.setHiddenPlayer(player);

        managerMessage.sendAnnouncement(player, "connect.quit", String.valueOf(Bukkit.getOnlinePlayers().size() - managerPlugin.getHiddenPlayers().size()));

        managerMessage.sendCommandMessage(player, "vanish.hide");

        managerMessage.playPlayerSound(player, "activate");

        return true;

    }

}
