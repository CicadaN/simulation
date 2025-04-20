package io.example.simulation.config;

public class SimulationConfig {
    private final int width;
    private final int height;
    private final int herbivoresCount;
    private final int predatorsCount;
    private final int grassCount;
    private final int grassPerTurn;
    private final int rockCount;
    private final int treeCount;


    private SimulationConfig(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.herbivoresCount = builder.herbivoresCount;
        this.predatorsCount = builder.predatorsCount;
        this.grassCount = builder.grassCount;
        this.grassPerTurn = builder.grassPerTurn;
        this.rockCount = builder.rockCount;
        this.treeCount = builder.treeCount;
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
    public int getRockCount() {
        return rockCount;
    }
    public int getTreeCount() {
        return treeCount; }


    public static class Builder {
        private int width;
        private int height;
        private int herbivoresCount;
        private int predatorsCount;
        private int grassCount;
        private int grassPerTurn;
        private int rockCount;
        private int treeCount;

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder herbivores(int count) {
            this.herbivoresCount = count;
            return this;
        }

        public Builder predators(int count) {
            this.predatorsCount = count;
            return this;
        }

        public Builder grass(int count) {
            this.grassCount = count;
            return this;
        }

        public Builder grassPerTurn(int count) {
            this.grassPerTurn = count;
            return this;
        }

        public Builder rocks(int count) {
            this.rockCount = count;
            return this;
        }

        public Builder trees(int count) {
            this.treeCount = count;
            return this;
        }


        public SimulationConfig build() {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Map size must be positive");
            }
            return new SimulationConfig(this);
        }
    }
}
