package io.example;

import io.example.map.WorldMap;
import io.example.simulation.Simulation;
import io.example.simulation.actions.*;
import io.example.simulation.config.Menu;
import io.example.simulation.config.SimulationConfig;
import io.example.simulation.interaction.CreatureInteractionHandler;
import io.example.simulation.render.ConsoleRenderer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Получаем конфигурацию
        Menu menu = new Menu();
        SimulationConfig config = menu.buildConfigWithMenu();

        // Создаём карту
        WorldMap worldMap = new WorldMap(config.getWidth(), config.getHeight());

        // Создаём обработчик взаимодействий
        CreatureInteractionHandler interactionHandler = new CreatureInteractionHandler(worldMap);

        // Список начальных действий
        Action init = new InitAction(
                config.getHerbivoresCount(),
                config.getPredatorsCount(),
                config.getGrassCount(),
                config.getTreeCount(),
                config.getRockCount()
        );

        // Список действий хода
        Action move = new MoveCreaturesAction(interactionHandler);
        List<Action> turnActions = List.of(
                new RemoveDeadCreaturesAction(),
                new MoveCreaturesAction(interactionHandler)
        );

        ConsoleRenderer renderer = new ConsoleRenderer();

        Simulation simulation = new Simulation(worldMap, List.of(init), turnActions, renderer);
        simulation.start();
    }
}
