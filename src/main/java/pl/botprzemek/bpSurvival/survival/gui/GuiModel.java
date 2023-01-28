package pl.botprzemek.bpSurvival.survival.gui;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.configs.ConfigGui;

public class GuiModel {

    private final ConfigGui configGui;
    private String title;
    private final int size;

    public GuiModel(ConfigGui configGui, String title, int size) {
        this.configGui = configGui;
        this.title = title;
        this.size = size;
    }

    public Gui createGui(Player player, String guiName) {
        title = getParsedTitle(player);
        return new Gui(player, guiName, title, size, configGui.loadButtons(player, guiName));
    }

    public String getParsedTitle(Player player) {
        try {
            Component serializedMessage = MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, "<white>" + title));
            return LegacyComponentSerializer.legacySection().serialize(serializedMessage);
        }
        catch (ParsingException error) {
            return title;
        }
    }
}
