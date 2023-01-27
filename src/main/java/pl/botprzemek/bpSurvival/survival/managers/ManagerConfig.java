package pl.botprzemek.bpSurvival.survival.managers;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.configs.ConfigGui;
import pl.botprzemek.bpSurvival.survival.configs.ConfigMessage;
import pl.botprzemek.bpSurvival.survival.configs.ConfigPlugin;
import pl.botprzemek.bpSurvival.survival.configs.ConfigProfile;
import pl.botprzemek.bpSurvival.survival.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class ManagerConfig {

    private final List<Config> configs = new ArrayList<>();
    private final ConfigPlugin configPlugin;
    private final ConfigMessage configMessage;
    private final ConfigProfile configProfile;
    private final ConfigGui configGui;

    public ManagerConfig(SurvivalPlugin survivalPlugin) {
        BpSurvival instance = survivalPlugin.getInstance();

        configs.add(configPlugin = new ConfigPlugin(instance, "config.yml"));
        configs.add(configMessage = new ConfigMessage(instance, "messages.yml"));
        configs.add(configProfile = new ConfigProfile(instance, "profiles.yml"));
        configs.add(configGui = new ConfigGui(instance, "guis.yml"));

        loadConfigs();
    }

    public void loadConfigs() {
        for (Config config : configs) config.loadConfig();
    }

    public void saveConfigs() {
        configProfile.saveConfig();
    }

    public ConfigPlugin getConfigPlugin() {
        return configPlugin;
    }

    public ConfigMessage getConfigMessage() {
        return configMessage;
    }

    public ConfigProfile getConfigProfile() {
        return configProfile;
    }

    public ConfigGui getConfigGui() {
        return configGui;
    }

}