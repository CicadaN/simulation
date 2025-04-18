package io.example.simulation.actions;

import io.example.entity.Grass;
import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.Random;

public class AddGrassAction implements Action {
    private final int count;
    private final Random random;

    public AddGrassAction(int count) {
        this.count = count;
        this.random = new Random();
    }

    //TODO проверка на заполнение карты
    @Override
    public void execute(WorldMap map) {
        for (int i = 0; i < count; i++) {
            Position position = getRandomPosition(map);
            if (!map.isOccupied(position)) {
                map.addEntity(position, new Grass());
            }
        }
    }

    private Position getRandomPosition(WorldMap map) {
        int x = random.nextInt(map.getWidth());
        int y = random.nextInt(map.getHeight());
        return new Position(x, y);
    }
} 