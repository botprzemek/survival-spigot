package pl.botprzemek.bpSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.botprzemek.bpSurvival.survival.gui.Gui;
import pl.botprzemek.bpSurvival.survival.managers.ManagerGui;
import pl.botprzemek.bpSurvival.survival.SurvivalPlugin;

import java.util.List;

public class CommandOpenGui implements CommandExecutor, TabCompleter {
    private final ManagerGui managerGui;

    public CommandOpenGui(SurvivalPlugin survivalPlugin) {
        managerGui = survivalPlugin.getManagerGui();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        Gui gui = (args.length != 0) ? managerGui.createGui(player, args[0]) : null;

        if (gui == null) return false;

        gui.openInventory();

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return managerGui.getGuisName();
        return null;
    }

}
