package pl.botprzemek.bpSurvival.SurvivalManager.Drop;

import pl.botprzemek.bpSurvival.SurvivalManager.Config.Configs.DropConfig;

import java.util.List;

public class DropManager {

    private final DropConfig dropConfig;

    private final List<Items> items;

    public DropManager(DropConfig dropConfig) {

        this.dropConfig = dropConfig;

        items = loadItems();

    }

    public List<Items> loadItems() {

        return dropConfig.loadItems();

    }

    public List<Items> getItems() {

        return items;

    }

}