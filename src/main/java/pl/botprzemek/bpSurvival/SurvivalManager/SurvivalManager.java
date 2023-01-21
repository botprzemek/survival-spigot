package pl.botprzemek.bpSurvival.SurvivalManager;

import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.Commands.CommandManager;
import pl.botprzemek.bpSurvival.Events.EventManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ProfileManager;

public class SurvivalManager {

    private final BpSurvival instance;

    private final ConfigManager configManager;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    private final ProfileManager profileManager;

    public SurvivalManager(BpSurvival instance) {

        this.instance = instance;

        configManager = new ConfigManager(this);

        pluginManager = new PluginManager(configManager.getPluginConfig());

        messageManager = new MessageManager(this);

        profileManager = new ProfileManager(configManager.getProfileConfig());

        new EventManager(this);

        new CommandManager(this);

    }

    public void cleanUp() {

        profileManager.saveProfiles();

        configManager.saveConfigs();

    }

    public BpSurvival getInstance() {

        return instance;

    }

    public ConfigManager getConfigManager() {

        return configManager;

    }

    public PluginManager getPluginManager() {

        return pluginManager;

    }

    public MessageManager getMessageManager() {

        return messageManager;

    }

    public ProfileManager getProfileManager() {

        return profileManager;

    }

}
