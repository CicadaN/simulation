package io.example;

import io.example.map.WorldMap;
import io.example.simulation.Simulation;
import io.example.simulation.actions.*;
import io.example.simulation.config.Menu;
import io.example.simulation.config.SimulationConfig;
import io.example.simulation.render.ConsoleRenderer;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Menu menu = new Menu();
        SimulationConfig config = menu.showMenu();

        WorldMap map = new WorldMap(config.getWidth(), config.getHeight());

        List<Action> initActions = List.of(
            new InitAction(
                config.getHerbivoresCount(),
                config.getPredatorsCount(),
                config.getGrassCount()
            )
        );

        List<Action> turnActions = List.of(
            new MoveCreaturesAction(map),
            new AddGrassAction(config.getGrassPerTurn())
        );

        ConsoleRenderer renderer = new ConsoleRenderer();

        Simulation simulation = new Simulation(map, initActions, turnActions, renderer);

        simulation.start();
    }
}

