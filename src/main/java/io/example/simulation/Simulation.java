package io.example.simulation;

import io.example.map.WorldMap;
import io.example.simulation.actions.Action;
import io.example.simulation.render.ConsoleRenderer;

import java.util.List;

public class Simulation {

    private final WorldMap map;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final ConsoleRenderer renderer;

    private boolean running = false;
    private int turnCounter = 0;

    public Simulation(WorldMap map,
                      List<Action> initActions,
                      List<Action> turnActions,
                      ConsoleRenderer renderer) {
        this.map = map;
        this.initActions = initActions;
        this.turnActions = turnActions;
        this.renderer = renderer;
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(map);
        }

        renderer.render(map, ++turnCounter);

        if (isSimulationOver()) {
            System.out.println("Симуляция завершена — одна из сторон исчезла.");
            stop();
        }
    }

    private boolean isSimulationOver() {
        boolean hasHerbivores = map.getEntitiesSnapshot().values().stream()
                .anyMatch(e -> e instanceof io.example.entity.creature.Herbivore);
        boolean hasPredators = map.getEntitiesSnapshot().values().stream()
                .anyMatch(e -> e instanceof io.example.entity.creature.Predator);

        return !hasHerbivores || !hasPredators;
    }


    public void start() {
        running = true;
        turnCounter = 1;

        System.out.println("=== Запуск симуляции ===");
        for (Action action : initActions) {
            action.execute(map);
        }

        renderer.render(map, turnCounter);

        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Симуляция прервана.");
                break;
            }

            nextTurn();
        }
    }

    public void stop() {
        this.running = false;
    }
}
