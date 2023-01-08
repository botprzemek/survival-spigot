package pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Config;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Profile;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.Settings;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.UUID;

public class ProfileConfig extends Config {

    HashMap<UUID, Profile> profiles = new HashMap<>();

    public ProfileConfig(BpSurvival instance, String file) {

        super(instance, file);

    }

    public HashMap<UUID, Profile> loadProfiles() {

        ConfigurationSection section = getConfigurationSection("");

        for (String path : section.getKeys(false)) profiles.put(UUID.fromString(path), getProfile(UUID.fromString(path)));

        return profiles;

    }

    public void saveProfiles() {

        for (UUID playerUUID : profiles.keySet()) setProfile(playerUUID, profiles.get(playerUUID));

    }

    public Profile getProfile(UUID playerUUID) {

        ConfigurationSection config = getConfigurationSection(playerUUID.toString());

        if (config == null) return null;

        int level = config.getInt("level");

        int exp = config.getInt("exp");

        ConfigurationSection settingsConfig = config.getConfigurationSection("settings");

        if (settingsConfig == null) return null;

        Settings settings = new Settings(
            settingsConfig.getBoolean("drop-to-inv"),
            settingsConfig.getBoolean("drop-mined"),
            settingsConfig.getInt("multiplier")
        );

        return new Profile(level, exp, settings);

    }

    public void setProfile(UUID playerUUID, Profile profile) {

        String path = playerUUID.toString();

        set(path + ".level",
            profile.getLevel()
        );

        set(path + ".exp",
                profile.getExp()
        );

        set(path + ".settings.drop-to-inv",
                profile.getSettings().isToInventory()
        );

        set(path + ".settings.drop-mined",
                profile.getSettings().isMinedBlock()
        );

        set(path + ".settings.multiplier",
                profile.getSettings().getMultiplier()
        );

    }

}
