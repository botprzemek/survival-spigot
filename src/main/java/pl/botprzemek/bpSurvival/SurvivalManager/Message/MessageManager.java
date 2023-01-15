package pl.botprzemek.bpSurvival.SurvivalManager.Message;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.MessageConfig;
import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;

public class MessageManager {

    private final MessageConfig messageConfig;

    private final BukkitAudiences adventure;

    private final StringSerializer stringSerializer;

    public MessageManager(SurvivalManager survivalManager) {

        this.messageConfig = survivalManager.getConfigManager().getMessageConfig();

        adventure = BukkitAudiences.create(survivalManager.getInstance());

        stringSerializer = new StringSerializer();

    }

    public void sendCommandMessage(Player player, String path) {

        String message = messageConfig.getCommandMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
            .replace("%prefix%", messageConfig.getPrefix()));

        adventure.player(player).sendMessage(serializedMessage);

    }

    public void sendCommandMessage(Player player, String path, String value) {

        String message = messageConfig.getCommandMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
            .replace("%prefix%", messageConfig.getPrefix())
            .replace("%value%", value));

        adventure.player(player).sendMessage(serializedMessage);

    }

    public void sendEventMessage(Player player, String path, String value) {

        String message = messageConfig.getEventMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
                .replace("%prefix%", messageConfig.getPrefix())
                .replace("%value%", value));

        adventure.player(player).sendMessage(serializedMessage);

    }

    public void sendTitle(Player player, String path) {

        ConfigurationSection section = messageConfig.getConfigurationSection(path);

        if (section == null) return;

        player.sendTitle(
            section.getString("title"),
            section.getString("description"),
            section.getInt("fadein") * 20,
            section.getInt("stay") * 20,
            section.getInt("fadeout") * 20
        );

    }

    public void sendAnnouncement(Player player, String path, String value) {

        String message = messageConfig.getEventMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
                .replace("%prefix%", messageConfig.getPrefix())
                .replace("%value%", value));

        adventure.all().sendMessage(serializedMessage);

    }

    public String getMessageString(Player player, String path) {

        String message = messageConfig.getMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
                .replace("%prefix%", messageConfig.getPrefix()));

        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);

    }

    public String getMessageString(Player player, String path, String value) {

        String message = messageConfig.getMessage(path);

        Component serializedMessage = stringSerializer.serializeString(player, message
                .replace("%prefix%", messageConfig.getPrefix())
                .replace("%value%", value));

        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);

    }

}
