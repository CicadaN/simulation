package io.example.simulation.config;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public SimulationConfig showMenu() {
        System.out.println("=== Симуляция мира ===");
        System.out.println("1. Запустить с настройками по умолчанию");
        System.out.println("2. Настроить параметры вручную");
        System.out.print("Выберите вариант: ");

        int choice = readInt();
        
        return choice == 1 ? getDefaultConfig() : getCustomConfig();
    }

    private SimulationConfig getDefaultConfig() {
        return new SimulationConfig(
            10,  // width
            10,  // height
            5,   // herbivores
            2,   // predators
            8,   // grass
            1    // grass per turn
        );
    }

    private SimulationConfig getCustomConfig() {
        System.out.println("\n=== Настройка параметров ===");

        int width = readInt("Введите ширину карты (5-20): ", 5, 20);
        int height = readInt("Введите высоту карты (5-20): ", 5, 20);

        final int DEFAULT_TREES = 10;
        int totalCells = width * height;

        // Не более 50% клеток под динамические сущности, оставим остальное под свободу
        int maxAvailableEntities = (totalCells - DEFAULT_TREES) / 2;

        if (maxAvailableEntities < 3) {
            System.out.println("Недостаточно места для симуляции. Попробуйте уменьшить размер деревьев или увеличить карту.");
            return getDefaultConfig();
        }

        int herbivores = readInt(
                "Введите количество травоядных (1-" + maxAvailableEntities + "): ",
                1, maxAvailableEntities
        );

        int freeAfterHerbivores = maxAvailableEntities - herbivores;

        int predators = 0;
        if (freeAfterHerbivores > 0) {
            predators = readInt(
                    "Введите количество хищников (1-" + freeAfterHerbivores + "): ",
                    1, freeAfterHerbivores
            );
        } else {
            System.out.println("Недостаточно места для хищников. Они не будут добавлены.");
        }

        int freeAfterPredators = freeAfterHerbivores - predators;

        int grass = 0;
        if (freeAfterPredators > 0) {
            grass = readInt(
                    "Введите количество травы (1-" + freeAfterPredators + "): ",
                    1, freeAfterPredators
            );
        } else {
            System.out.println("Недостаточно места для травы. Она не будет добавлена.");
        }

        int grassPerTurn = readInt("Введите количество восстанавливаемой травы за ход (1-3): ", 1, 3);

        return new SimulationConfig(width, height, herbivores, predators, grass, grassPerTurn);
    }


    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Введите число от %d до %d\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число");
            }
        }
    }

    private int readInt() {
        return readInt("", 1, 2);
    }
} 