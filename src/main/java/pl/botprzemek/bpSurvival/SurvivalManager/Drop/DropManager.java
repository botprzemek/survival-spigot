package pl.botprzemek.bpSurvival.SurvivalManager.Drop;

import org.bukkit.Material;
import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.DropConfig;

import java.util.List;

public class DropManager {

    private final DropConfig dropConfig;

    private final List<Material> blocks;

    private final List<Items> items;

    public DropManager(DropConfig dropConfig) {

        this.dropConfig = dropConfig;

        blocks = loadBlocks();

        items = loadItems();

    }

    public List<Material> loadBlocks() {

        if (blocks != null) blocks.clear();

        return dropConfig.loadBlocks();

    }

    public List<Material> getBlocks() {

        return blocks;

    }

    public List<Items> loadItems() {

        if (items != null) items.clear();

        return dropConfig.loadItems();

    }

    public List<Items> getItems() {

        return items;

    }

}