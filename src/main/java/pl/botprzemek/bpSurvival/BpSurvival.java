package pl.botprzemek.bpSurvival;

import org.bukkit.plugin.java.JavaPlugin;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

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
