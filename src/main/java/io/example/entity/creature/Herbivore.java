package io.example.entity.creature;

import io.example.entity.Grass;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.pathfinder.AStarPathFinder;

import java.util.List;
import java.util.function.Predicate;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH = 100;
    public static final int SPEED = 1;
    public static final int FOOD_VALUE = 20;
    private static final AStarPathFinder pathFinder = new AStarPathFinder();

    public Herbivore() {
        super(SPEED, MAX_HEALTH, FOOD_VALUE);
    }

    @Override
    public Position makeMove(WorldMap map, Position currentPosition) {
        Predicate<Position> isGoal = pos ->
                map.getEntity(pos).filter(e -> e instanceof Grass).isPresent();

        List<Position> path = pathFinder.findPath(map, currentPosition, isGoal);

        if (!path.isEmpty()) {
            int stepIndex = Math.min(speed, path.size() - 1);
            return path.get(stepIndex);
        }

        return currentPosition;
    }

    public void eat() {
        this.health = Math.min(MAX_HEALTH, this.health + FOOD_VALUE);
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

}
