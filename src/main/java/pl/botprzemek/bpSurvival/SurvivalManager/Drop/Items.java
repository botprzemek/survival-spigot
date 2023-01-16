package pl.botprzemek.bpSurvival.SurvivalManager.Drop;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Items {

    private final ItemStack item;

    private final double chance;

    private final int minAmount;

    private final int maxAmount;

    public Items(ConfigurationSection section) {

        item = OraxenItems.getItemById(section.getString("id")).build();

        chance = section.getDouble("chance");

        minAmount = section.getInt("min");

        maxAmount = section.getInt("max");

    }

    public boolean shouldDrop(Random random) {

        return random.nextInt( 100) < chance;

    }

    public ItemStack createItem(ThreadLocalRandom random, int multiplier) {

        int amount = random.nextInt(minAmount, maxAmount+1);

        if (item == null) return null;

        this.item.setAmount(amount * multiplier);

        return item;

    }

}
