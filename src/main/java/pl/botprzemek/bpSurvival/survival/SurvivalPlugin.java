package pl.botprzemek.bpSurvival.survival;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.*;

public class SurvivalPlugin {
    private final BpSurvival instance;
    private Economy managerEconomy;
    private final ManagerConfig managerConfig;
    private final ManagerPlugin managerPlugin;
    private final ManagerMessage managerMessage;
    private final ManagerProfile managerProfile;
    private final ManagerGui managerGui;

    public SurvivalPlugin(BpSurvival instance) {
        this.instance = instance;

        if (!setupEconomy()) instance.getServer().shutdown();

        managerConfig = new ManagerConfig(this);
        managerPlugin = new ManagerPlugin(managerConfig.getConfigPlugin());
        managerMessage = new ManagerMessage(this);
        managerProfile = new ManagerProfile(managerConfig.getConfigProfile());
        managerGui = new ManagerGui(managerConfig.getConfigGui());

        new ManagerListeners(this);
    }

    private boolean setupEconomy() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> provider = instance.getServer().getServicesManager().getRegistration(Economy.class);
        if (provider == null) return false;
        managerEconomy = provider.getProvider();
        return true;
    }

    public void cleanUp() {
        managerProfile.saveProfiles();
        managerConfig.saveConfigs();
    }

    public BpSurvival getInstance() {
        return instance;
    }

    public Economy getManagerEconomy() {
        return managerEconomy;
    }

    public ManagerConfig getManagerConfig() {
        return managerConfig;
    }

    public ManagerPlugin getManagerPlugin() {
        return managerPlugin;
    }

    public ManagerMessage getManagerMessage() {
        return managerMessage;
    }

    public ManagerProfile getManagerProfile() {
        return managerProfile;
    }

    public ManagerGui getManagerGui() {
        return managerGui;
    }

}
