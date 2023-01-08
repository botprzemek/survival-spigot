package pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs;

import org.bukkit.configuration.ConfigurationSection;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Config;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.Items;

import java.util.ArrayList;
import java.util.List;

public class DropConfig extends Config {

    List<Items> items = new ArrayList<>();

    public DropConfig(BpSurvival instance, String file) {

        super(instance, file);

    }

    public List<Items> loadItems() {

        ConfigurationSection section = getConfigurationSection("");

        for (String path : section.getKeys(false)) items.add(new Items(getConfigurationSection(path)));

        return items;

    }

}
