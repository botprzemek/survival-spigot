package pl.botprzemek.bpSurvival.SurvivalManager.Message;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class StringSerializer {

    private final MiniMessage mm;

    public StringSerializer() {

        mm = MiniMessage.miniMessage();

    }

    public Component serializeString(String message) {

        return mm.deserialize(message);

    }

    public Component serializeString(Player player,  String message) {

        return mm.deserialize(PlaceholderAPI.setPlaceholders(player, message));

    }

}
