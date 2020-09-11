package cz.iwitrag.procdungen.generator;

/**
 * Represents error which occurred during dungeon generation
 */
public class GeneratorException extends Exception {
    public GeneratorException(String message) {
        super(message);
    }
}