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
        System.out.printf("=== Turn #%d ===%n", ++turnCounter);
        for (Action action : turnActions) {
            action.execute(map);
        }
        renderer.render(map);
    }

    public void start() {
        running = true;

        System.out.println("=== Initializing Simulation ===");
        for (Action action : initActions) {
            action.execute(map);
        }

        renderer.render(map);

        while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation interrupted.");
                break;
            }

            nextTurn();
        }
    }

    public void stop() {
        this.running = false;
    }
}
