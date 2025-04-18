package io.example.simulation.config;

public class SimulationConfig {
    private final int width;
    private final int height;
    private final int herbivoresCount;
    private final int predatorsCount;
    private final int grassCount;
    private final int grassPerTurn;

    public SimulationConfig(int width, int height, int herbivoresCount, int predatorsCount, int grassCount, int grassPerTurn) {
        this.width = width;
        this.height = height;
        this.herbivoresCount = herbivoresCount;
        this.predatorsCount = predatorsCount;
        this.grassCount = grassCount;
        this.grassPerTurn = grassPerTurn;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHerbivoresCount() {
        return herbivoresCount;
    }

    public int getPredatorsCount() {
        return predatorsCount;
    }

    public int getGrassCount() {
        return grassCount;
    }

    public int getGrassPerTurn() {
        return grassPerTurn;
    }
} 