package pl.botprzemek.bpSurvival.survival.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.utils.Config;
import pl.botprzemek.bpSurvival.survival.utils.Profile;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ConfigProfile extends Config {

    HashMap<UUID, Profile> profiles = new HashMap<>();

    public ConfigProfile(BpSurvival instance, String file) {

        super(instance, file);

    }

    public HashMap<UUID, Profile> loadProfiles() {

        ConfigurationSection section = getConfigurationSection("");

        if (section == null) return profiles;

        for (String path : section.getKeys(false)) profiles.put(UUID.fromString(path), getProfile(UUID.fromString(path)));

        return profiles;

    }

    public void saveProfiles() {

        for (UUID playerUUID : profiles.keySet()) setProfile(playerUUID, profiles.get(playerUUID));

    }

    public Profile getProfile(UUID playerUUID) {

        ConfigurationSection config = getConfigurationSection(playerUUID.toString());

        if (config == null) return new Profile(new HashMap<>(), new HashMap<>());

        ConfigurationSection homesConfig = config.getConfigurationSection("homes");

        HashMap<String, Location> homes = new HashMap<>();

        if (homesConfig != null) {

            for (String homeName : homesConfig.getKeys(false)) {

                ConfigurationSection homeConfig = homesConfig.getConfigurationSection(homeName);

                if (homeConfig != null) {

                    Location location = new Location(

                            Bukkit.getWorld(Objects.requireNonNull(homeConfig.getString("world"))),

                            homeConfig.getDouble("x"),

                            homeConfig.getDouble("y"),

                            homeConfig.getDouble("z")

                    );

                    homes.put(homeName, location);

                }

            }

        }

        ConfigurationSection cooldownsConfig = config.getConfigurationSection("cooldowns");

        HashMap<String, Long> cooldowns = new HashMap<>();

        if (cooldownsConfig != null) {

            for (String cooldownSection : cooldownsConfig.getKeys(false)) {

                ConfigurationSection cooldownConfig = cooldownsConfig.getConfigurationSection(cooldownSection);

                if (cooldownConfig != null) {

                    for (String cooldownName : cooldownConfig.getKeys(false)) {

                        Long time = cooldownConfig.getLong(cooldownName);

                        cooldowns.put(cooldownSection + "." + cooldownName, time);

                    }

                }

            }

        }

        return new Profile(homes, cooldowns);

    }

    public void setProfile(UUID playerUUID, Profile profile) {

        String path = playerUUID.toString();

        for (String homeName : profile.getHomes().keySet()) {

            Location location = profile.getHomes().get(homeName);

            set(path + ".homes." + homeName + ".world",
                Objects.requireNonNull(location.getWorld()).getName()
            );

            set(path + ".homes." + homeName + ".x",
                location.getX()
            );

            set(path + ".homes." + homeName + ".y",
                location.getY()
            );

            set(path + ".homes." + homeName + ".z",
                location.getZ()
            );

        }

        for (String cooldownName : profile.getCooldowns().keySet()) {

            set(path + ".cooldowns." + cooldownName,
                profile.getCooldowns().get(cooldownName)
            );

        }

    }

}
