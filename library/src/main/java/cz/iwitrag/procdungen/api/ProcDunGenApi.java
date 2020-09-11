package cz.iwitrag.procdungen.api;

import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.InvalidConfigurationException;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.generator.implementations.physical.ConfiguredGenerator;

/**
 * Entry point of library, used to generate dungeons
 */
public class ProcDunGenApi {

    /**
     * Generates new dungeon based on provided configuration
     * @param configuration Dungeon configuration to use with generator
     * @return Generated dungeon
     * @throws InvalidConfigurationException If configuration was invalid or failed to load
     * @throws GeneratorException If generator was unable to generate dungeon because of error
     */
    public Dungeon generateDungeon(DungeonConfiguration configuration) throws InvalidConfigurationException, GeneratorException {
        return generateDungeonPartially(configuration, ConfiguredGenerator.Phase.CREATE_CORRIDORS);
    }

    /**
     * Generates new dungeon based on provided configuration and stops after specified phase
     * @param configuration Dungeon configuration to use with generator
     * @param stopAfterPhase Which phase should be the last
     * @return Generated dungeon (may be incomplete based on phase generator stopped at)
     * @throws InvalidConfigurationException If configuration was invalid or failed to load
     * @throws GeneratorException If generator was unable to generate dungeon because of error
     */
    public Dungeon generateDungeonPartially(DungeonConfiguration configuration, ConfiguredGenerator.Phase stopAfterPhase) throws InvalidConfigurationException, GeneratorException {
        configuration.validate();
        ConfiguredGenerator generator = new ConfiguredGenerator(configuration);
        generator.setStopAfterPhase(stopAfterPhase);
        return generator.generate();
    }

}
