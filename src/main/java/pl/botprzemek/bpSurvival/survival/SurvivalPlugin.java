package pl.botprzemek.bpSurvival.survival;

import com.google.common.collect.Lists;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.botprzemek.bpSurvival.BpSurvival;
import pl.botprzemek.bpSurvival.survival.managers.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, () -> {
            List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());
            int randomPlayer = ThreadLocalRandom.current().nextInt(players.size());
            Bukkit.getLogger().info();
        }, 0, (5*20));

        new ManagerEvent(this);
        new ManagerCommand(this);

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
