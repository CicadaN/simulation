package io.example.simulation.actions;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.Map;

public class RemoveDeadCreaturesAction implements Action {

    @Override
    public void execute(WorldMap map) {
        Map<Position, Entity> snapshot = map.getEntitiesSnapshot();
        for (Map.Entry<Position, Entity> entry : snapshot.entrySet()) {
            Entity entity = entry.getValue();
            if (entity instanceof Creature creature && creature.isDead()) {
                map.removeEntity(entry.getKey());
            }
        }
    }
}
