package cz.iwitrag.procdungen;

import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.InvalidConfigurationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class YamlConfigurationTests {

    static File configuration;

    @BeforeAll
    static void prepareFile() throws IOException {
        configuration = new File("test.yml");
        if (configuration.delete())
            System.out.println("Deleted previous test file");
        if (!configuration.createNewFile())
            System.err.println("Failed to create test file");
    }

    @AfterAll
    static void cleanFile() {
        if (!configuration.delete())
            System.err.println("Failed to delete test file");
    }

    @Test
    @Disabled
    void testEmptyConfiguration() throws InvalidConfigurationException {
        DungeonConfiguration dungeonConfiguration = DungeonConfiguration.fromFile(configuration);
        Assertions.assertNotNull(dungeonConfiguration, "DungeonConfiguration cannot be null");
    }

}
