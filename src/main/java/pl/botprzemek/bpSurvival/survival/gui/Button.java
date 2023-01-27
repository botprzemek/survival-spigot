package pl.botprzemek.bpSurvival.survival.gui;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.botprzemek.bpSurvival.survival.gui.enums.TypeAction;
import pl.botprzemek.bpSurvival.survival.gui.enums.TypeItem;

import java.util.List;

public class Button {
    private final String itemName;
    private final String action;
    private final int slot;
    private final TypeItem typeItem;
    private final TypeAction typeAction;
    private final String displayName;
    private final List<String> lore;

    public Button(String itemName, String action, int slot, TypeItem typeItem, TypeAction typeAction, String displayName, List<String> lore) {
        this.itemName = itemName;
        this.action = action;
        this.slot = slot;
        this.typeItem = typeItem;
        this.typeAction = typeAction;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack getItem() {
        return !typeItem.equals(TypeItem.ORAXEN) ? new ItemStack(Material.valueOf(itemName.toUpperCase())) : OraxenItems.getItemById(itemName).build();
    }

    public ItemStack getPlaceholderItem() {
        ItemStack item = typeItem.equals(TypeItem.MINECRAFT) ? new ItemStack(Material.valueOf(itemName.toUpperCase())) : OraxenItems.getItemById(itemName).build();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public int getSlot() {
        return slot;
    }

    public String getAction() {
        return action;
    }

    public TypeAction getTypeAction() {
        return typeAction;
    }

}
