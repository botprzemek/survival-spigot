package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.argument.Args;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;

@Route(name = "message", aliases = "msg")
@Permission("bpsurvival.player.command.message")
public class CommandMessage {
    @Inject ManagerPlugin managerPlugin;
    @Inject ManagerMessage managerMessage;

    @Execute
    public void onMessage(Player player, @Args String playerName, @Joiner String message) {
        if (message == null) {
            managerMessage.sendCommandMessage(player, "message.invalid");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            managerMessage.sendCommandMessage(player, "message.offline");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        if (target.equals(player)) {
            managerMessage.sendCommandMessage(player, "message.same");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        if (managerPlugin.isStreamingPlayer(target)) {
            managerMessage.sendCommandMessage(player, "message.deny");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        managerMessage.sendMessageToReceiver(managerPlugin, player, target, message);
    }
}