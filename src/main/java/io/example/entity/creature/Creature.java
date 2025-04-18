package io.example.entity.creature;

import io.example.entity.Entity;
import io.example.map.Position;
import io.example.map.WorldMap;

public abstract class Creature extends Entity {

    protected int speed;
    protected int health;

    public Creature(int speed, int health, int foodValue) {
        this.speed = speed;
        this.health = health;
    }

    public abstract Position makeMove(WorldMap map, Position position);

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public abstract int getMaxHealth();

}
