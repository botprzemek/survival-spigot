package pl.botprzemek.bpSurvival.SurvivalManager;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import pl.botprzemek.bpSurvival.Commands.CommandManager;
import pl.botprzemek.bpSurvival.Events.EventManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Configuration.PluginManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Drop.DropManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Message.MessageManager;
import pl.botprzemek.bpSurvival.SurvivalManager.Profile.ProfileManager;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.ConfigManager;

public class SurvivalManager {

    private final BpSurvival instance;

    private final BukkitAudiences adventure;

    private final ConfigManager configManager;

    private final PluginManager pluginManager;

    private final MessageManager messageManager;

    private final ProfileManager profileManager;

    private final DropManager dropManager;

    public SurvivalManager(BpSurvival instance) {

        this.instance = instance;

        adventure = BukkitAudiences.create(getInstance());

        configManager = new ConfigManager(this);

        pluginManager = new PluginManager(configManager.getPluginConfig());

        messageManager = new MessageManager(configManager.getMessageConfig(), getAdventure());

        profileManager = new ProfileManager(configManager.getProfileConfig());

        dropManager = new DropManager(configManager.getDropConfig());

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

    public BukkitAudiences getAdventure() {

        return adventure;

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

    public DropManager getDropManager() {

        return dropManager;

    }

}
