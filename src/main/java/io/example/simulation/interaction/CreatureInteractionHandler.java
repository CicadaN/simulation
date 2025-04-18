package io.example.simulation.interaction;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.entity.creature.Herbivore;
import io.example.entity.creature.Predator;
import io.example.entity.Grass;
import io.example.map.Position;
import io.example.map.WorldMap;

public class CreatureInteractionHandler {

    private final WorldMap map;

    public CreatureInteractionHandler(WorldMap map) {
        this.map = map;
    }

    public void handleInteraction(Creature creature, Position current, Position target) {
        if (!map.isOccupied(target)) {
            map.moveEntity(current, target);
            return;
        }

        Entity targetEntity = map.getEntity(target).orElseThrow();

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
            map.removeEntity(preyPos);
            map.moveEntity(predatorPos, preyPos);
            predator.heal();
        }
    }

    private void handleHerbivoreEat(Herbivore herbivore, Position herbivorePos, Position grassPos) {
        map.removeEntity(grassPos);
        map.moveEntity(herbivorePos, grassPos);
        herbivore.eat();
    }
}
