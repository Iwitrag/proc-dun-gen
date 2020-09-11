package cz.iwitrag.procdungen.api.configuration;

/**
 * Represents error in {@link DungeonConfiguration} itself or an error when parsing configuration from a File
 */
public class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
