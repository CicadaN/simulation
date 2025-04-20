package io.example.simulation.actions;

import io.example.entity.Entity;
import io.example.entity.creature.Creature;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.simulation.interaction.CreatureInteractionHandler;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MoveCreaturesAction implements Action {

    private final CreatureInteractionHandler interactionHandler;

    public MoveCreaturesAction(CreatureInteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    @Override
    public void execute(WorldMap worldMap) {
        Map<Position, Entity> snapshot = worldMap.getEntitiesSnapshot();
        List<MoveCommand> moveCommands = new ArrayList<>();

        for (Map.Entry<Position, Entity> entry : snapshot.entrySet()) {
            Entity entity = entry.getValue();
            if (!(entity instanceof Creature creature)) {
                continue;
            }

            Position current = entry.getKey();
            Position target = creature.makeMove(worldMap, current);

            if (target != null && !current.equals(target)) {
                moveCommands.add(new MoveCommand(creature, current, target));
            }
        }

        for (MoveCommand move : moveCommands) {
            try {
                interactionHandler.handleInteraction(move.creature(), move.from(), move.to());
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.err.println("Move failed: " + e.getMessage());
            }
        }
    }

    private record MoveCommand(Creature creature, Position from, Position to) {}
}
