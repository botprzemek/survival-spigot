package pl.botprzemek.bpSurvival.SurvivalManager;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigMessage;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigPlugin;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigProfile;
import pl.botprzemek.bpSurvival.SurvivalManager.Utils.Config;

import java.util.ArrayList;
import java.util.List;

public class ManagerConfig {

    private final List<Config> configs = new ArrayList<>();

    private final ConfigPlugin configPlugin;

    private final ConfigMessage configMessage;

    private final ConfigProfile configProfile;

    public ManagerConfig(ManagerSurvival managerSurvival) {

        BpSurvival instance = managerSurvival.getInstance();

        configs.add(this.configPlugin = new ConfigPlugin(instance, "config.yml"));

        configs.add(this.configMessage = new ConfigMessage(instance, "messages.yml"));

        configs.add(this.configProfile = new ConfigProfile(instance, "profiles.yml"));

        loadConfigs();

    }

    public void loadConfigs() {

        for (Config config : configs) {

            config.loadConfig();

        }

    }

    public void saveConfigs() {

        configProfile.saveConfig();

    }

    public ConfigPlugin getPluginConfig() {

        return configPlugin;

    }

    public ConfigMessage getMessageConfig() {

        return configMessage;

    }

    public ConfigProfile getProfileConfig() {

        return configProfile;

    }

}