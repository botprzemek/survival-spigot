package pl.botprzemek.bpSurvival.SurvivalManager.Config;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Config;

public class MessageConfig extends Config {

    public MessageConfig(BpSurvival instance, String file) {

        super(instance, file);

    }

    public String getCommandMessage(String path) {

        ConfigurationSection section = getConfigurationSection("commands");

        if (section == null) return null;

        return section.getString(path);

    }

    public String getEventMessage(String path) {

        ConfigurationSection section = getConfigurationSection("events");

        if (section == null) return null;

        return section.getString(path);

    }


    public String getMessage(String path) {

        return getString(path);

    }

    public Sound getSound(String path) {

        String soundName = getString("sounds." + path);

        if (soundName == null) return Sound.ENTITY_PLAYER_LEVELUP;

        return Sound.valueOf(soundName.toUpperCase());

    }

    public String getPrefix() {

        return getString("prefix");

    }

}
