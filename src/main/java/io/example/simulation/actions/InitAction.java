package io.example.simulation.actions;

import io.example.entity.Entity;
import io.example.entity.Rock;
import io.example.entity.creature.Herbivore;
import io.example.entity.creature.Predator;
import io.example.entity.Grass;
import io.example.entity.Tree;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.util.MapUtils;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.Collections;

public class InitAction implements Action {

    private final int herbivoresCount;
    private final int predatorsCount;
    private final int grassCount;
    private final int treeCount;
    private final int rockCount;
    private final Random random = new Random();

    public InitAction(int herbivores, int predators, int grass, int trees, int rockCount) {
        this.herbivoresCount = herbivores;
        this.predatorsCount = predators;
        this.grassCount = grass;
        this.treeCount = trees;
        this.rockCount = rockCount;
    }

    @Override
    public void execute(WorldMap worldMap) {
        List<Position> freePositions = MapUtils.allPositions(worldMap);
        Collections.shuffle(freePositions, random);

        int index = 0;

        index += spawn(worldMap, freePositions, index, Herbivore::new, herbivoresCount);
        index += spawn(worldMap, freePositions, index, () -> new Predator(10), predatorsCount);
        index += spawn(worldMap, freePositions, index, Grass::new, grassCount);
        index += spawn(worldMap, freePositions, index, Tree::new, treeCount);
        index += spawn(worldMap, freePositions, index, Rock::new, rockCount);
    }

    private int spawn(WorldMap map, List<Position> positions, int startIndex,
                      Supplier<Entity> factory, int count) {
        int spawned = 0;

        for (int i = 0; i < count && startIndex + i < positions.size(); i++) {
            Position pos = positions.get(startIndex + i);
            map.addEntity(pos, factory.get());
            spawned++;
        }

        return spawned;
    }
}
