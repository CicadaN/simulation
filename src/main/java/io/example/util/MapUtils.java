package io.example.util;

import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public final class MapUtils {

    private MapUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<Position> allPositions(WorldMap map) {
        List<Position> result = new ArrayList<>();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                result.add(new Position(x, y));
            }
        }
        return result;
    }

    public static int manhattanDistance(Position a, Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
