package io.example.entity.creature;

import io.example.entity.Entity;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.pathfinder.AStarPathFinder;

import java.util.List;

public abstract class Creature extends Entity {
    protected int health;
    protected final int speed;
    protected final int foodValue;

    protected static final AStarPathFinder pathFinder = new AStarPathFinder();

    protected Creature(int speed, int maxHealth, int foodValue) {
        this.speed = speed;
        this.health = maxHealth;
        this.foodValue = foodValue;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Negative damage");
        health = Math.max(0, health - damage);
    }

    public int getHealth() {
        return health;
    }

    public abstract int getMaxHealth();

    protected abstract boolean isFood(Entity entity);

    public Position makeMove(WorldMap worldMap, Position currentPosition) {
        List<Position> path = pathFinder.findPath(worldMap, currentPosition, this::isFood);

        if (!path.isEmpty()) {
            int stepIndex = Math.min(speed, path.size() - 1);
            return path.get(stepIndex);
        }

        return currentPosition;
    }

    public void interact(Entity target, WorldMap map, Position current, Position targetPos) {
        if (!isFood(target)) {
            return;
        }

        map.removeEntity(current);
        map.removeEntity(targetPos); // "съесть"
        map.addEntity(targetPos, this);
    }
}
