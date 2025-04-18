package io.example.simulation.actions;

import io.example.entity.Tree;
import io.example.entity.creature.Herbivore;
import io.example.entity.creature.Predator;
import io.example.entity.Grass;
import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InitAction implements Action {
    private final int numGrass;
    private final int numPredators;
    private final int numHerbivores;
    private final int DEFAULT_TREES = 10;
    private final int attackPower = 30;

    private final Random random = new Random();

    int addedHerbivores = 0;
    int addedPredators = 0;
    int addedGrass = 0;

    public InitAction(int numHerbivores, int numPredators, int numGrass) {
        this.numGrass = numGrass;
        this.numPredators = numPredators;
        this.numHerbivores = numHerbivores;
    }

    @Override
    public void execute(WorldMap map) {
        List<Position> positionList = map.allPositions();
        Collections.shuffle(positionList, random);

        int index = 0;

        for (int i = 0; i < numHerbivores && index < positionList.size(); i++) {
            map.addEntity(positionList.get(index++), new Herbivore());
            addedHerbivores++;
        }
        for (int i = 0; i < numPredators && index < positionList.size(); i++) {
            map.addEntity(positionList.get(index++), new Predator(attackPower));
            addedPredators++;
        }
        for (int i = 0; i < numGrass && index < positionList.size(); i++) {
            map.addEntity(positionList.get(index++), new Grass());
            addedGrass++;
        }
        for (int i = 0; i < DEFAULT_TREES && index < positionList.size(); i++) {
            map.addEntity(positionList.get(index++), new Tree());
        }
        System.out.printf("Создано: %d травоядных, %d хищников, %d трав\n",
                addedHerbivores, addedPredators, addedGrass);
    }

}