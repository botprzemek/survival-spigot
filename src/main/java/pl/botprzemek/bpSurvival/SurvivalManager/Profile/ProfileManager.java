package pl.botprzemek.bpSurvival.SurvivalManager.Profile;

import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.ProfileConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager {

    private final ProfileConfig profileConfig;

    private final HashMap<UUID, Profile> profiles;

    public ProfileManager(ProfileConfig profileConfig) {

        this.profileConfig = profileConfig;

        profiles = loadProfiles();

    }

    public void createProfile(Player player) {

        Profile profile = new Profile(0, 0, new Settings(false, true, 1), new HashMap<>(), new HashMap<>());

        UUID playerUUID = player.getUniqueId();

        profiles.put(playerUUID, profile);

    }

    public Profile getProfile(Player player) {

        return profiles.get(player.getUniqueId());

    }

    public void saveProfiles() {

        profileConfig.saveProfiles();

    }

    public HashMap<UUID, Profile> loadProfiles() {

        return profileConfig.loadProfiles();

    }

}
