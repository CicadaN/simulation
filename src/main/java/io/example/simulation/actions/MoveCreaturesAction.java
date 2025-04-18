package io.example.simulation.actions;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.simulation.interaction.CreatureInteractionHandler;

import java.util.Map;

public class MoveCreaturesAction implements Action {
    private final CreatureInteractionHandler interactionHandler;

    public MoveCreaturesAction(WorldMap map) {
        this.interactionHandler = new CreatureInteractionHandler(map);
    }

    @Override
    public void execute(WorldMap map) {
        Map<Position, Entity> entities = map.getCopyEntity();

        for (Map.Entry<Position, Entity> entry : entities.entrySet()) {
            Position current = entry.getKey();
            Entity entity = entry.getValue();

            if (entity instanceof Creature creature) {
                if (creature.isDead()) continue; // Доп проверка, нужна ли?

                Position target = creature.makeMove(map, current);

                if (!target.equals(current) && map.isWithinBounds(target)) {
                    interactionHandler.handleInteraction(creature, current, target);
                }
            }
        }
    }
} 