package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.Room;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Point;
import cz.iwitrag.procdungen.generator.Generator;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * The only one current implementation of generator<br>
 * Generates dungeons based on provided configuration
 */
public class ConfiguredGenerator implements Generator {

    private DungeonConfiguration configuration;
    private Phase stopAfterPhase;

    public ConfiguredGenerator(DungeonConfiguration configuration) {
        this.configuration = configuration;
        this.stopAfterPhase = Phase.CREATE_CORRIDORS;
    }

    public Phase getStopAfterPhase() {
        return stopAfterPhase;
    }

    public void setStopAfterPhase(Phase stopAfterPhase) {
        this.stopAfterPhase = stopAfterPhase;
    }

    @Override
    public Dungeon generate() throws GeneratorException {
        // Try to read seed string from configuration as number if possible
        RandomGenerator randomGenerator;
        try {
            randomGenerator = new RandomGenerator(Long.parseLong(configuration.getSeed()));
        } catch (NumberFormatException ex) {
            randomGenerator = new RandomGenerator(configuration.getSeed());
        }
        Dungeon dungeon = new Dungeon();
        dungeon.setSeed(randomGenerator.getSeed());

        List<Long> generationTimes = new ArrayList<>();
        generationTimes.add(System.currentTimeMillis()); // 0 - START

        /////////////////////////////////////////////////////

        //System.out.println("Placing rooms");
        if (stopAfterPhase.compareTo(Phase.PLACE_ROOMS) >= 0)
            placeRooms(dungeon, randomGenerator);
        generationTimes.add(System.currentTimeMillis()); // 1 - PLACE ROOMS

        /////////////////////////////////////////////////////

        //System.out.println("Spreading rooms");
        if (stopAfterPhase.compareTo(Phase.SPREAD_ROOMS) >= 0)
            spreadRooms(dungeon, randomGenerator);
        generationTimes.add(System.currentTimeMillis()); // 2 - SPREAD ROOMS

        /////////////////////////////////////////////////////

        //System.out.println("Connecting rooms");
        if (stopAfterPhase.compareTo(Phase.CONNECT_ROOMS) >= 0)
            connectRooms(dungeon, randomGenerator);
        generationTimes.add(System.currentTimeMillis()); // 3 - CONNECT ROOMS

        /////////////////////////////////////////////////////

        //System.out.println("Creating corridors");
        if (stopAfterPhase.compareTo(Phase.CREATE_CORRIDORS) >= 0)
            createCorridors(dungeon, randomGenerator);
        generationTimes.add(System.currentTimeMillis()); // 4 - CREATE CORRIDORS

        /////////////////////////////////////////////////////

        //System.out.println(generationTimes.get(4) - generationTimes.get(0));

        normalizeCoordinates(dungeon, randomGenerator);
        return dungeon;
    }

    private void placeRooms(Dungeon dungeon, RandomGenerator randomGenerator) throws GeneratorException {
        RoomPopulator roomPopulator = new RoomPopulator(dungeon, randomGenerator);
        for (Room room : configuration.getRooms()) {
            roomPopulator.generateRoomsFromConfig(room, new Point<>(0, 0), 10, 10);
        }
    }

    private void spreadRooms(Dungeon dungeon, RandomGenerator randomGenerator) throws GeneratorException {
        RoomSpreader roomSpreader = new RoomSpreader(dungeon, randomGenerator);
        roomSpreader.spreadRooms();
    }

    private void connectRooms(Dungeon dungeon, RandomGenerator randomGenerator) throws GeneratorException {
        RoomConnector roomConnector = new RoomConnector(dungeon, randomGenerator);
        roomConnector.connectRooms();
    }

    private void createCorridors(Dungeon dungeon, RandomGenerator randomGenerator) throws GeneratorException {
        CorridorMaker corridorMaker = new CorridorMaker(dungeon, randomGenerator);
        corridorMaker.createCorridors();
    }

    private void normalizeCoordinates(Dungeon dungeon, RandomGenerator randomGenerator) throws GeneratorException {
        CoordinatesNormalizer coordinatesNormalizer = new CoordinatesNormalizer(dungeon, randomGenerator);
        coordinatesNormalizer.normalizeCoordinates();
    }

    public enum Phase implements Comparable<Phase> {
        INIT, PLACE_ROOMS, SPREAD_ROOMS, CONNECT_ROOMS, CREATE_CORRIDORS;
    }
}
