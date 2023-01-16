package pl.botprzemek.bpSurvival.SurvivalManager.Configuration;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {

    private final String name;

    private final int cooldown;

    private final List<ItemStack> items;

    public Kit(String name, int cooldown, List<ItemStack> items) {

        this.name = name;

        this.cooldown = cooldown;

        this.items = items;

    }

    public String getName() {

        return name;

    }

    public int getCooldown() {

        return cooldown;

    }

    public List<ItemStack> getItems() {

        return items;

    }

}
