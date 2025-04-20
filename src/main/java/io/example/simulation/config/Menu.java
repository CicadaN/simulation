package io.example.simulation.config;

import java.util.Scanner;

public class Menu {
    private static final int MAP_SIZE_MIN = 5;
    private static final int MAP_SIZE_MAX = 20;
    private static final int GRASS_PER_TURN_MIN = 1;
    private static final int GRASS_PER_TURN_MAX = 3;

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;
    private static final int DEFAULT_HERBIVORES = 5;
    private static final int DEFAULT_PREDATORS = 2;
    private static final int DEFAULT_GRASS = 8;
    private static final int DEFAULT_GRASS_PER_TURN = 1;
    private static final int DEFAULT_TREES = 10;
    private static final int DEFAULT_ROCK = 5;

    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public SimulationConfig buildConfigWithMenu() {
        System.out.println("=== Симуляция мира ===");
        System.out.println("1. Запустить с настройками по умолчанию");
        System.out.println("2. Настроить параметры вручную");
        System.out.print("Выберите вариант: ");

        int choice = readInt("", 1, 2);
        return choice == 1 ? getDefaultConfig() : getCustomConfig();
    }

    private SimulationConfig getDefaultConfig() {
        return new SimulationConfig.Builder()
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .herbivores(DEFAULT_HERBIVORES)
                .predators(DEFAULT_PREDATORS)
                .grass(DEFAULT_GRASS)
                .grassPerTurn(DEFAULT_GRASS_PER_TURN)
                .rocks(DEFAULT_ROCK)
                .trees(DEFAULT_TREES)
                .build();
    }

    private SimulationConfig getCustomConfig() {
        System.out.println("\n=== Настройка параметров ===");

        int width = readInt(
                "Введите ширину карты (%d-%d): ".formatted(MAP_SIZE_MIN, MAP_SIZE_MAX),
                MAP_SIZE_MIN, MAP_SIZE_MAX
        );

        int height = readInt(
                "Введите высоту карты (%d-%d): ".formatted(MAP_SIZE_MIN, MAP_SIZE_MAX),
                MAP_SIZE_MIN, MAP_SIZE_MAX
        );

        int totalCells = width * height;
        int maxAvailableEntities = (totalCells - DEFAULT_TREES) / 2;

        if (maxAvailableEntities < 3) {
            System.out.println("Недостаточно места для симуляции. Значения по умолчанию.");
            return getDefaultConfig();
        }

        int herbivores = readInt(
                "Введите количество травоядных (1-%d): ".formatted(maxAvailableEntities),
                1, maxAvailableEntities
        );

        int remaining = maxAvailableEntities - herbivores;
        int predators = 0;
        if (remaining > 0) {
            predators = readInt(
                    "Введите количество хищников (1-%d): ".formatted(remaining),
                    1, remaining
            );
        } else {
            System.out.println("Недостаточно места для хищников.Они не будут добавлены.");
        }

        remaining -= predators;
        int grass = 0;
        if (remaining > 0) {
            grass = readInt(
                    "Введите количество травы (1-%d): ".formatted(remaining),
                    1, remaining
            );
        } else {
            System.out.println("Недостаточно места для травы. Она не будет добавлена.");
        }

        int grassPerTurn = readInt(
                "Введите количество восстанавливаемой травы за ход (%d-%d): ".formatted(GRASS_PER_TURN_MIN, GRASS_PER_TURN_MAX),
                GRASS_PER_TURN_MIN, GRASS_PER_TURN_MAX
        );

        return new SimulationConfig.Builder()
                .width(width)
                .height(height)
                .herbivores(herbivores)
                .predators(predators)
                .grass(grass)
                .grassPerTurn(grassPerTurn)
                .rocks(DEFAULT_ROCK)     // можно тоже сделать настраиваемым позже
                .trees(DEFAULT_TREES)    // см. выше
                .build();

    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Введите число от %d до %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число");
            }
        }
    }
}
