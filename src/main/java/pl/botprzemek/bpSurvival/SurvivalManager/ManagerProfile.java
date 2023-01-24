package pl.botprzemek.bpSurvival.SurvivalManager;

import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Profile;

import java.util.HashMap;
import java.util.UUID;

public class ManagerProfile {

    private final ConfigProfile configProfile;

    private final HashMap<UUID, Profile> profiles;

    public ManagerProfile(ConfigProfile configProfile) {

        this.configProfile = configProfile;

        profiles = loadProfiles();

    }

    public void createProfile(Player player) {

        Profile profile = new Profile(new HashMap<>(), new HashMap<>());

        UUID playerUUID = player.getUniqueId();

        profiles.put(playerUUID, profile);

    }

    public Profile getProfile(Player player) {

        return profiles.get(player.getUniqueId());

    }

    public void saveProfiles() {

        configProfile.saveProfiles();

    }

    public HashMap<UUID, Profile> loadProfiles() {

        return configProfile.loadProfiles();

    }

}
