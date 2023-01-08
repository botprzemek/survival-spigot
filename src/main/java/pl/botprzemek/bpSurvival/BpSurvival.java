package pl.botprzemek.bpSurvival;

import pl.botprzemek.bpSurvival.SurvivalManager.SurvivalManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BpSurvival extends JavaPlugin {

    private SurvivalManager survivalManager;

    @Override
    public void onEnable() {

        survivalManager = new SurvivalManager(this);

    }

    @Override
    public void onDisable() {

        survivalManager.cleanUp();

    }

}
