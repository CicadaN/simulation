package io.example.simulation.render;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class ConsoleRenderer {

    private static final Map<Class<?>, String> SPRITES = new HashMap<>();

    static {
        SPRITES.put(io.example.entity.Grass.class, "🌿");
        SPRITES.put(io.example.entity.Tree.class, "🌳");
        SPRITES.put(io.example.entity.creature.Herbivore.class, "🐄");
        SPRITES.put(io.example.entity.creature.Predator.class, "🐅");
        SPRITES.put(io.example.entity.Rock.class, "🗻");
    }

    private static final String FREE_CELL = "⬛";

    public void render(WorldMap worldMap, int turnNumber) {
        System.out.printf("=== Ход #%d ===%n", turnNumber);

        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                Position pos = new Position(x, y);

                if (!worldMap.isOccupied(pos)) {
                    System.out.print(FREE_CELL);
                    continue;
                }

                Entity entity = worldMap.getEntity(pos)
                        .orElseThrow(() -> new IllegalStateException("Occupied cell has no entity: " + pos));

                System.out.print(getColor(entity) + getSprite(entity) + AnsiColors.RESET);
            }
            System.out.println();
        }
    }

    private String getSprite(Entity entity) {
        String sprite = SPRITES.get(entity.getClass());
        if (sprite == null) {
            throw new IllegalStateException("No sprite for entity class: " + entity.getClass().getSimpleName());
        }
        return sprite;
    }

    private String getColor(Entity entity) {
        if (!(entity instanceof Creature creature)) {
            return AnsiColors.RESET;
        }

        double ratio = (double) creature.getHealth() / creature.getMaxHealth();
        if (ratio < 0.3) return AnsiColors.BACKGROUND_RED;
        if (ratio < 0.7) return AnsiColors.BACKGROUND_YELLOW;
        return AnsiColors.BACKGROUND_GREEN;
    }
}
