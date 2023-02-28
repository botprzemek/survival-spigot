package pl.botprzemek.bpSurvival.survival.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.configs.ConfigMessage;

import java.util.Objects;

public class ManagerMessage {

    private final ConfigMessage configMessage;
    private final BukkitAudiences adventure;
    private final MiniMessage mm;

    public ManagerMessage(SurvivalPlugin survivalPlugin) {
        configMessage = survivalPlugin.getManagerConfig().getConfigMessage();
        adventure = BukkitAudiences.create(survivalPlugin.getInstance());
        mm = MiniMessage.miniMessage();
    }

    public Component serializeString(Player player,  String message) {
        return mm.deserialize(PlaceholderAPI.setPlaceholders(player, message));
    }

    public Component serializeString(String message) {
        return mm.deserialize(message);
    }

    public void sendCommandMessage(Player player, String path) {
        String message = configMessage.getCommandMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix()));

        adventure.player(player).sendMessage(serializedMessage);
    }

    public void sendCommandMessage(Player player, String path, String value) {
        String message = configMessage.getCommandMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix())
            .replace("%value%", value));

        adventure.player(player).sendMessage(serializedMessage);
    }

    public void sendEventMessage(Player player, String path) {
        String message = configMessage.getEventMessage(path);
        Component serializedMessage = serializeString(player, message
                .replace("%prefix%", configMessage.getPrefix()));

        adventure.player(player).sendMessage(serializedMessage);
    }

    public void sendEventMessage(Player player, String path, String value) {
        String message = configMessage.getEventMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix())
            .replace("%value%", value));

        adventure.player(player).sendMessage(serializedMessage);
    }

    public void sendTitle(Player player, String path) {
        ConfigurationSection section = configMessage.getConfigurationSection(path);

        if (section == null) return;

        player.sendTitle(
            Objects.requireNonNull(section.getString("title")),
            Objects.requireNonNull(section.getString("description")),
            section.getInt("fadein") * 20,
            section.getInt("stay") * 20,
            section.getInt("fadeout") * 20
        );
    }

    public void sendTitle(Player player, String path, String value1, String value2) {
        ConfigurationSection section = configMessage.getConfigurationSection(path);

        if (section == null) return;

        player.sendTitle(
            Objects.requireNonNull(section.getString("title"))
                .replace("%value%", value1),
            Objects.requireNonNull(section.getString("description"))
                .replace("%value%", value2),
            section.getInt("fadein") * 20,
            section.getInt("stay") * 20,
            section.getInt("fadeout") * 20
        );
    }

    public void sendAnnouncement(Player player, String path, String value) {
        String message = configMessage.getEventMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix())
            .replace("%value%", value));

        adventure.all().sendMessage(serializedMessage);
    }

    public void sendAnnouncement(String path, String value) {
        String message = configMessage.getEventMessage(path);
        Component serializedMessage = serializeString(message
                .replace("%prefix%", configMessage.getPrefix())
                .replace("%value%", value));

        adventure.all().sendMessage(serializedMessage);
    }

    public String getMessageString(Player player, String path) {
        String message = configMessage.getMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix()));

        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);
    }

    public String getMessageString(Player player, String path, String value) {
        String message = configMessage.getMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix())
            .replace("%value%", value));

        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);
    }

    public String getMessageString(Player player, String path, String value, String playerName) {
        String message = configMessage.getMessage(path);
        Component serializedMessage = serializeString(player, message
            .replace("%prefix%", configMessage.getPrefix())
            .replace("%player%", playerName)
            .replace("%value%", value));

        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);
    }

    public void playPlayerSound(Player player, String path) {
        player.playSound(player, configMessage.getSound(path), 1F, 1F);
    }

    public void playPlayerSound(String path) {
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player, configMessage.getSound(path), 1F, 1F);
    }

    public void sendMessageToReceiver(ManagerPlugin managerPlugin, Player player, Player target, String message) {
        target.sendMessage(getMessageString(target, "commands.message.format.receiver", message, player.getDisplayName()));
        player.sendMessage(getMessageString(player, "commands.message.format.sender", message, target.getDisplayName()));

        managerPlugin.setReplyPlayer(target, player);
        managerPlugin.setReplyPlayer(player, target);
    }
}
