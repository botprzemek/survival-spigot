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
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;

import java.util.Objects;
import java.util.UUID;

public class CommandVanish implements CommandExecutor {

    private final BpSurvival instance;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    public CommandVanish(ManagerSurvival managerSurvival) {

        instance = managerSurvival.getInstance();

        managerPlugin = managerSurvival.getPluginManager();

        managerMessage = managerSurvival.getMessageManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (managerPlugin.isHiddenPlayer(player)) {

            player.setMetadata("vanished", new FixedMetadataValue(instance, false));

            managerPlugin.clearHiddenPlayer(player);

            player.setGameMode(GameMode.CREATIVE);

            for (Player target : Bukkit.getOnlinePlayers()) target.showPlayer(instance, player);

            for (UUID playerUUID : managerPlugin.getHiddenPlayers()) Objects.requireNonNull(Bukkit.getPlayer(playerUUID)).showPlayer(instance, player);

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
