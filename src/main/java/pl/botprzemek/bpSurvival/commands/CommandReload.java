package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;
import pl.botprzemek.bpSurvival.survival.managers.*;

@Route(name = "bpsurvival", aliases = "bps")
@Permission("bpsurvival.admin.command.reload")
public class CommandReload {
    private final ManagerConfig managerConfig;
    private final ManagerProfile managerProfile;
    private final ManagerMessage managerMessage;
    private final ManagerPlugin managerPlugin;
    private final ManagerGui managerGui;

    public CommandReload(SurvivalPlugin survivalPlugin) {
        managerConfig = survivalPlugin.getManagerConfig();
        managerProfile = survivalPlugin.getManagerProfile();
        managerMessage = survivalPlugin.getManagerMessage();
        managerPlugin = survivalPlugin.getManagerPlugin();
        managerGui = survivalPlugin.getManagerGui();
    }

    @Execute
    public void reload(LiteSender sender) {
        try {
            managerProfile.saveProfiles();
            managerConfig.saveConfigs();
            managerConfig.loadConfigs();
            managerProfile.loadProfiles();
            managerPlugin.loadConfigs();
            managerGui.loadGuis();
            if (!(sender instanceof Player player)) return;
            managerMessage.sendCommandMessage(player, "reload.success");
            managerMessage.playPlayerSound(player, "activate");
        }
        catch (Exception error) {
            if (!(sender instanceof Player player)) return;
            managerMessage.sendCommandMessage(player, "reload.failed");
            managerMessage.playPlayerSound(player, "error");
        }
    }
}
