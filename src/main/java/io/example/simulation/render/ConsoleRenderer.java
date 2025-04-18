package io.example.simulation.render;

import io.example.entity.*;
import io.example.entity.creature.Creature;
import io.example.entity.creature.Herbivore;
import io.example.entity.creature.Predator;
import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.Map;

public class ConsoleRenderer {
    private static final String FREE_CELL_SPRITE = "⬛"; // ⬛
    private static final String ROCK_SPRITE = "🗻"; // 🗻
    private static final String TREE_SPRITE = "🌳"; // 🌳
    private static final String GRASS_SPRITE =  "🌿"; // 🌿
    private static final String HERBIVORE_SPRITE = "🐄"; // 🐄
    private static final String PREDATOR_SPRITE = "🐅"; // 🐅
    private static final String UNKNOWN_SPRITE = "❓"; // ❓

    private static final boolean SHOW_HEALTH = true;

    private static final Map<Class<? extends Entity>, String> SPRITES = Map.ofEntries(
            Map.entry(Rock.class, ROCK_SPRITE),
            Map.entry(Tree.class, TREE_SPRITE),
            Map.entry(Grass.class, GRASS_SPRITE),
            Map.entry(Herbivore.class, HERBIVORE_SPRITE),
            Map.entry(Predator.class, PREDATOR_SPRITE)
    );

    public void render(WorldMap map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Position pos = new Position(x, y);
                if (!map.isOccupied(pos)) {
                    System.out.print(FREE_CELL_SPRITE);
                } else {
                    Entity entity = map.getEntity(pos).orElseThrow();
                    String sprite = toSprite(entity);
                    System.out.print(sprite);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private String toSprite(Entity entity) {
        if (entity == null) return UNKNOWN_SPRITE;

        if (entity instanceof Creature creature) {
            String sprite = SPRITES.getOrDefault(entity.getClass(), UNKNOWN_SPRITE);
            return SHOW_HEALTH ? getHealthColored(sprite, creature) : sprite;
        }

        return SPRITES.getOrDefault(entity.getClass(), UNKNOWN_SPRITE);
    }

    private String getHealthColored(String sprite, Creature creature) {
        double healthRate = (double) creature.getHealth() / creature.getMaxHealth();
        String color = switch (getHealthLevel(healthRate)) {
            case LOW -> AnsiColors.BACKGROUND_RED;
            case MID -> AnsiColors.BACKGROUND_YELLOW;
            case FULL -> AnsiColors.BACKGROUND_GREEN;
        };
        return color + sprite + AnsiColors.RESET;
    }

    private enum HealthLevel { LOW, MID, FULL }

    private HealthLevel getHealthLevel(double healthRate) {
        if (healthRate < 0.3) return HealthLevel.LOW;
        if (healthRate < 0.6) return HealthLevel.MID;
        return HealthLevel.FULL;
    }
} 