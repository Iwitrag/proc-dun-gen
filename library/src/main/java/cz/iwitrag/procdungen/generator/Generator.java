package cz.iwitrag.procdungen.generator;

import cz.iwitrag.procdungen.api.data.Dungeon;

/**
 * Dungeon generator
 */
public interface Generator {

    /**
     * Generates new dungeon
     * @return Generated dungeon
     * @throws GeneratorException When generator was unable to generate new dungeon
     */
    Dungeon generate() throws GeneratorException;

}
