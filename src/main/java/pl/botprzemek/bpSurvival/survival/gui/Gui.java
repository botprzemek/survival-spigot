package pl.botprzemek.bpSurvival.survival.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.UUID;

public class Gui {

    private final Inventory inventory;
    private final UUID playerUUID;
    private final List<Button> buttons;

    public Gui(Player player, String title, int size, List<Button> buttons) {
        this.playerUUID = player.getUniqueId();
        this.buttons = buttons;

        inventory = Bukkit.createInventory(player, size, title);

        fillInventory(this.buttons);
    }

    public void openInventory() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void fillInventory(List<Button> buttons) {
        for (Button button : buttons) inventory.setItem(button.getSlot(), button.getPlaceholderItem());
    }

    public Button getButton(int slot) {
        for (Button button : buttons) if (button.getSlot() == slot) return button;
        return null;
    }

}
