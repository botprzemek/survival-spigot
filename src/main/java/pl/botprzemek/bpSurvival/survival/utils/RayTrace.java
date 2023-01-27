package pl.botprzemek.bpSurvival.survival.utils;

import org.bukkit.util.Vector;

import java.util.ArrayList;

public class RayTrace {

    private final Vector origin, direction;

    public RayTrace(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector getPosition(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    public ArrayList<Vector> getAllTraverseLocations(double blocksAway, double acurracy) {
        ArrayList<Vector> positions = new ArrayList<>();

        for (double d = 0; d < blocksAway; d += acurracy) positions.add(getPosition(d));

        return positions;
    }

}
