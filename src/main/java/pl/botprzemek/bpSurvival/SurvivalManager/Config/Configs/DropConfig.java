package pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Config;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DropConfig extends Config {

    List<Material> blocks = new ArrayList<>();

    List<Items> items = new ArrayList<>();

    public DropConfig(BpSurvival instance, String file) {

        super(instance, file);

    }

    public List<Material> loadBlocks() {

        List<String> blocksName = getStringList("blocks");

        for (String block : blocksName) blocks.add(Material.valueOf(block.toUpperCase()));

        return blocks;

    }

    public List<Items> loadItems() {

        ConfigurationSection section = getConfigurationSection("items");

        if (section == null) return new ArrayList<>();

        for (String path : section.getKeys(false)) items.add(new Items(Objects.requireNonNull(section.getConfigurationSection(path))));

        return items;

    }

}
