package pl.botprzemek.bpSurvival.survival.configs;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.gui.Button;
import pl.botprzemek.bpSurvival.survival.gui.GuiModel;
import pl.botprzemek.bpSurvival.survival.gui.enums.TypeAction;
import pl.botprzemek.bpSurvival.survival.gui.enums.TypeItem;
import pl.botprzemek.bpSurvival.survival.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigGui extends Config {
    private final HashMap<String, GuiModel> guiModels;

    public ConfigGui(BpSurvival instance, String file) {
        super(instance, file);
        guiModels = new HashMap<>();
    }

    public void loadGuis() {
        ConfigurationSection guisSection = getConfigurationSection("");

        if (guisSection == null) return;

        for (String guiName : guisSection.getKeys(false)) {
            ConfigurationSection guiSection = guisSection.getConfigurationSection(guiName);

            if (guiSection == null) return;

            String title = guiSection.getString("title");
            int size = guiSection.getInt("size");

            guiModels.put(guiName, new GuiModel(this, title, size));
        }
    }

    public List<Button> loadButtons(Player player, String guiName) {
        List<Button> buttons = new ArrayList<>();
        ConfigurationSection guiSection = getConfigurationSection(guiName);

        if (guiSection == null) return null;

        ConfigurationSection buttonsSection = guiSection.getConfigurationSection("buttons");

        if (buttonsSection == null) return null;

        for (String buttonName : buttonsSection.getKeys(false)) {
            ConfigurationSection buttonSection = buttonsSection.getConfigurationSection(buttonName);

            if (buttonSection == null) return null;

            int slot = buttonSection.getInt("slot");
            String name = buttonSection.getString("item-id");
            String action = buttonSection.getString("action");
            String typeItem = buttonSection.getString("item-type");
            String typeAction = buttonSection.getString("action-type");
            String displayName = buttonSection.getString("display-name");
            List<String> lore = buttonSection.getStringList("lore");

            if (typeItem == null) continue;
            if (typeAction == null) continue;

            buttons.add(new Button(name, action, slot, TypeItem.valueOf(typeItem.toUpperCase()), TypeAction.valueOf(typeAction.toUpperCase()), getParsedString(player, displayName), getParsedLore(player, lore)));
        }

        return buttons;
    }

    public HashMap<String, GuiModel> getGuiModels() {
        return guiModels;
    }

    public GuiModel getGuiModel(String guiName) {
        return guiModels.get(guiName);
    }


    public String getParsedString(Player player, String string) {
        Component serializedMessage = MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, string));
        return LegacyComponentSerializer.legacySection().serialize(serializedMessage);
    }

    public List<String> getParsedLore(Player player, List<String> lore) {
        lore.replaceAll(string -> getParsedString(player, string));
        return lore;
    }
}
