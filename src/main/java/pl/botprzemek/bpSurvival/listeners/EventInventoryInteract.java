package pl.botprzemek.bpSurvival.listeners;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.gui.Button;
import pl.botprzemek.bpSurvival.survival.gui.Gui;
import pl.botprzemek.bpSurvival.survival.managers.ManagerGui;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

import java.util.Objects;

public class EventInventoryInteract implements Listener {
    private final ManagerPlugin managerPlugin;
    private final ManagerGui managerGui;
    private final Economy managerEconomy;

    public EventInventoryInteract(SurvivalPlugin survivalPlugin) {
        managerPlugin = survivalPlugin.getManagerPlugin();
        managerGui = survivalPlugin.getManagerGui();
        managerEconomy = survivalPlugin.getManagerEconomy();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Gui gui = managerGui.getGui(player);

        if (gui == null) return;
        if (!event.getInventory().equals(gui.getInventory())) return;
        event.setCancelled(true);

        Button button = gui.getButton(event.getSlot());

        if (button == null) return;
        switch (button.getTypeAction()) {
            case NULL -> managerGui.refreshGui(player, gui.getName());
            case CLOSE -> player.closeInventory();
            case COMMAND -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), button.getAction());
            case OPEN -> {
                Gui newGui = managerGui.createGui(player, button.getAction());
                newGui.openInventory();
            }
            case SHOP -> {
                Inventory inventory = player.getInventory();

                switch (event.getClick()) {
                    case LEFT -> {
                        double price = Double.parseDouble(button.getAction());

                        for (ItemStack item : inventory.getContents()) {
                            if (item == null || !item.isSimilar(button.getItem())) continue;

                            item.setAmount(item.getAmount() - 1);
                            player.updateInventory();
                            managerEconomy.depositPlayer(player, price);
                            managerGui.refreshGui(player, gui.getName());

                            return;
                        }
                    }
                    case RIGHT -> {
                        double price = Double.parseDouble(button.getAction()) * 3;

                        if (managerEconomy.getBalance(player) < price) return;

                        managerEconomy.withdrawPlayer(player, price);
                        managerGui.refreshGui(player, gui.getName());

                        inventory.addItem(button.getItem());
                    }
                    case SHIFT_LEFT -> {
                        int amount = 0;

                        for (ItemStack item : inventory.getContents()) {
                            if (item == null || !item.isSimilar(button.getItem())) continue;
                            amount += item.getAmount();
                            inventory.removeItem(item);
                        }

                        double price = Double.parseDouble(button.getAction()) * amount;

                        managerEconomy.depositPlayer(player, price);
                        managerGui.refreshGui(player, gui.getName());
                    }
                    case SHIFT_RIGHT -> {
                        double price = Double.parseDouble(button.getAction()) * 3 * 64;

                        if (managerEconomy.getBalance(player) < price) return;

                        managerEconomy.withdrawPlayer(player, price);
                        managerGui.refreshGui(player, gui.getName());

                        ItemStack item = button.getItem();

                        item.setAmount(64);
                        inventory.addItem(item);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Gui gui = managerGui.getGui((Player) event.getWhoClicked());
        if (gui != null) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrop(PlayerDropItemEvent event) {
        Gui gui = managerGui.getGui(event.getPlayer());
        if (gui != null) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Gui gui = managerGui.getGui(player);
        if (gui == null) return;
        if (event.getInventory().equals(gui.getInventory())) managerGui.removeGui(player);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!Objects.equals(managerPlugin.getSpawnLocation().getWorld(), event.getPlayer().getWorld())) return;
        if (event.getInventory().getType().equals(InventoryType.ANVIL)) {
            if (!event.getPlayer().hasPermission("bpsurvival.protection")) event.setCancelled(true);
        }
    }

}
