package io.example.simulation.interaction;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.entity.creature.Herbivore;
import io.example.entity.creature.Predator;
import io.example.entity.Grass;
import io.example.map.Position;
import io.example.map.WorldMap;

public class CreatureInteractionHandler {

    private final WorldMap worldMap;

    public CreatureInteractionHandler(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void handleInteraction(Creature creature, Position current, Position target) {
        if (!worldMap.isWithinBounds(current) || !worldMap.isWithinBounds(target)) {
            throw new IllegalArgumentException("Invalid coordinates: " + current + " -> " + target);
        }

        if (!worldMap.isOccupied(current)) {
            throw new IllegalStateException("No entity at position: " + current);
        }

        if (!worldMap.isOccupied(target)) {
            move(current, target);
            return;
        }

        Entity targetEntity = worldMap.getEntity(target).orElseThrow(() ->
                new IllegalStateException("Target position is expected to have an entity: " + target)
        );

        if (creature instanceof Predator predator && targetEntity instanceof Herbivore herbivore) {
            handlePredatorHunt(predator, herbivore, current, target);
            return;
        }

        if (creature instanceof Herbivore herbivore && targetEntity instanceof Grass) {
            handleHerbivoreEat(herbivore, current, target);
        }
    }

    private void handlePredatorHunt(Predator predator, Herbivore prey, Position predatorPos, Position preyPos) {
        predator.attack(prey);
        if (prey.isDead()) {
            worldMap.removeEntity(preyPos);
            move(predatorPos, preyPos);
            predator.heal();
        }
    }

    private void handleHerbivoreEat(Herbivore herbivore, Position herbivorePos, Position grassPos) {
        worldMap.removeEntity(grassPos);
        move(herbivorePos, grassPos);
        herbivore.eat();
    }

    private void move(Position from, Position to) {
        Entity entity = worldMap.getEntity(from)
                .orElseThrow(() -> new IllegalStateException("No entity at position: " + from));

        if (worldMap.isOccupied(to)) {
            throw new IllegalStateException("Target already occupied: " + to);
        }

        worldMap.removeEntity(from);
        worldMap.addEntity(to, entity);
    }
}
