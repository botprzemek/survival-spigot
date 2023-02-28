package pl.botprzemek.bpSurvival.commands;

import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.botprzemek.bpSurvival.survival.managers.ManagerMessage;
import pl.botprzemek.bpSurvival.survival.managers.ManagerPlugin;


@Route(name = "reply", aliases = "r")
@Permission("bpsurvival.player.command.reply")
public class CommandReply {
    private final ManagerPlugin managerPlugin;
    private final ManagerMessage managerMessage;

    @Execute
    public void onReply(Player player, @Joiner String message) {

        if (message == null) {
            managerMessage.sendCommandMessage(player, "message.invalid");
            managerMessage.playPlayerSound(player, "error");
            return;
        }

        Player target = Bukkit.getPlayer(managerPlugin.getReplyPlayer(player));

        if (target == null) {
            managerMessage.sendCommandMessage(player, "message.offline");
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
