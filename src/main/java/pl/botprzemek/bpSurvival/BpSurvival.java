package pl.botprzemek.bpSurvival;

import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BpSurvival extends JavaPlugin {

    private SurvivalPlugin survivalPlugin;

    @Override
    public void onEnable() {

        survivalPlugin = new SurvivalPlugin(this);

    }

    @Override
    public void onDisable() {

        if (survivalPlugin != null) survivalPlugin.cleanUp();

    }

}
