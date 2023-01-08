package pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs;

import org.bukkit.configuration.ConfigurationSection;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Config;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.Items;

import java.util.ArrayList;
import java.util.List;

public class MessageConfig extends Config {

    List<Items> items = new ArrayList<>();

    public MessageConfig(BpSurvival instance, String file) {

        super(instance, file);

    }

    public String getCommandMessage(String path) {

        ConfigurationSection section = getConfigurationSection("commands");

        if (section == null) return null;

        return section.getString(path);

    }

    public String getPrefix() {

        return getString("prefix");

    }

}
