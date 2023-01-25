package pl.botprzemek.bpSurvival.SurvivalManager;

import pl.botprzemek.bpSurvival.BpSurvival;

public class ManagerSurvival {

    private final BpSurvival instance;

    private final ManagerConfig managerConfig;

    private final ManagerPlugin managerPlugin;

    private final ManagerMessage managerMessage;

    private final ManagerProfile managerProfile;

    public ManagerSurvival(BpSurvival instance) {

        this.instance = instance;

        managerConfig = new ManagerConfig(this);

        managerPlugin = new ManagerPlugin(managerConfig.getPluginConfig());

        managerMessage = new ManagerMessage(this);

        managerProfile = new ManagerProfile(managerConfig.getProfileConfig());

        new ManagerEvent(this);

        new ManagerCommand(this);

    }

    public void cleanUp() {

        managerProfile.saveProfiles();

        managerConfig.saveConfigs();

    }

    public BpSurvival getInstance() {

        return instance;

    }

    public ManagerConfig getConfigManager() {

        return managerConfig;

    }

    public ManagerPlugin getPluginManager() {

        return managerPlugin;

    }

    public ManagerMessage getMessageManager() {

        return managerMessage;

    }

    public ManagerProfile getProfileManager() {

        return managerProfile;

    }

}
