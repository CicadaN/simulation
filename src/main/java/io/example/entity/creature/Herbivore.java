package io.example.entity.creature;

import io.example.entity.Entity;
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
    protected boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public void eat() {
        this.health = Math.min(MAX_HEALTH, this.health + FOOD_VALUE);
    }

}
