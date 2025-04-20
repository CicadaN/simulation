package io.example.entity.creature;

import io.example.entity.Entity;
import io.example.pathfinder.AStarPathFinder;

public class Predator extends Creature {

    private final int attackPower;

    public static final int SPEED = 2;
    public static final int MAX_HEALTH = 50;
    public static final int FOOD_VALUE = 20;

    private static final AStarPathFinder pathFinder = new AStarPathFinder();

    public Predator(int attackPower) {
        super(SPEED, MAX_HEALTH, FOOD_VALUE);
        this.attackPower = attackPower;
    }

    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    public void attack(Herbivore herbivore) {
        herbivore.takeDamage(this.attackPower);
    }

    public void heal() {
        this.health = Math.min(MAX_HEALTH, this.health + FOOD_VALUE);
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }
}
