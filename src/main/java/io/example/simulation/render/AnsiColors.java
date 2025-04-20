package io.example.simulation.render;

public final class AnsiColors {
    public static final String RESET = "\u001B[0m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";

    private AnsiColors() {
        throw new UnsupportedOperationException("Utility class");
    }
}
