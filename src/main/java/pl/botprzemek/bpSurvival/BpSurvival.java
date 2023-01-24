package pl.botprzemek.bpSurvival;

import pl.botprzemek.bpSurvival.SurvivalManager.ManagerSurvival;
import org.bukkit.plugin.java.JavaPlugin;

public class    BpSurvival extends JavaPlugin {

    private ManagerSurvival managerSurvival;

    @Override
    public void onEnable() {

        managerSurvival = new ManagerSurvival(this);

    }

    @Override
    public void onDisable() {

        if (managerSurvival != null) managerSurvival.cleanUp();

    }

}
