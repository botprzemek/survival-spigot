package pl.botprzemek.bpSurvival.survival.managers;

import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.configs.ConfigGui;
import pl.botprzemek.bpSurvival.survival.gui.Gui;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ManagerGui {

    private final ConfigGui configGui;
    private final HashMap<UUID, Gui> playerGuis;

    public ManagerGui(ConfigGui configGui) {
        this.configGui = configGui;
        playerGuis = new HashMap<>();
        loadGuis();
    }

    public void loadGuis() {
        configGui.loadGuis();
    }

    public List<String> getGuisName() {
        return configGui.getGuiModels().keySet().stream().toList();
    }

    public Gui createGui(Player player, String guiName) {
        Gui gui = configGui.getGuiModel(guiName).createGui(player, guiName);
        playerGuis.put(player.getUniqueId(), gui);
        return gui;
    }

    public void removeGui(Player player) {
        playerGuis.remove(player.getUniqueId());
    }

    public Gui getGui(Player player) {
        return playerGuis.get(player.getUniqueId());
    }
}
