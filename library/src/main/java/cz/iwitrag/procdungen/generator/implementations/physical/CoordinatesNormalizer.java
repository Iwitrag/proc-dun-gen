package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.data.Corridor;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Room;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.RandomGenerator;

import java.util.Set;

/**
 * Normalized dungeon coordinates so all rooms and corridors have non-negative X and Y only
 */
public class CoordinatesNormalizer {

    private Dungeon dungeon;

    public CoordinatesNormalizer(Dungeon dungeon, RandomGenerator randomGenerator) {
        this.dungeon = dungeon;
    }

    public void normalizeCoordinates() throws GeneratorException {
        try {
            normalize();
        } catch (Exception ex) {
            throw new GeneratorException("Error while normalizing coordinates: " + ex.getMessage());
        }
    }

    private void normalize() {
        Set<Room> rooms = dungeon.getRooms();
        Set<Corridor> corridors = dungeon.getCorridors();
        if (rooms.isEmpty() && corridors.isEmpty())
            return;
        int lowestX = 0;
        int lowestY = 0;
        for (Room room : rooms) {
            if (room.getPosition().getX() < lowestX)
                lowestX = room.getPosition().getX();
            if (room.getPosition().getY() < lowestY)
                lowestY = room.getPosition().getY();
        }
        for (Corridor corridor : corridors) {
            if (corridor.getPosition().getX() < lowestX)
                lowestX = corridor.getPosition().getX();
            if (corridor.getPosition().getY() < lowestY)
                lowestY = corridor.getPosition().getY();
        }
        int absLowestX = Math.abs(lowestX);
        int absLowestY = Math.abs(lowestY);
        rooms.forEach((room) -> room.setPosition(
                room.getPosition().getX() + absLowestX,
                room.getPosition().getY() + absLowestY
        ));
        corridors.forEach((corridor) -> corridor.setPosition(
                corridor.getPosition().getX() + absLowestX,
                corridor.getPosition().getY() + absLowestY
        ));
    }
}
