package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Point;
import cz.iwitrag.procdungen.api.data.Room;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class checks for all rooms in dungeon and spreads them evenly so they don't overlap.<br>
 * This is done by constantly checking pairs of rooms in a loop and moving them by minimum needed amount in random angle
 * to stop overlapping.
 */
public class RoomSpreader {

    private Dungeon dungeon;
    private RandomGenerator randomGenerator;

    public RoomSpreader(Dungeon dungeon, RandomGenerator randomGenerator) {
        this.dungeon = dungeon;
        this.randomGenerator = randomGenerator;
    }

    public void spreadRooms() throws GeneratorException {
        try {
            spread();
        } catch (Exception ex) {
            throw new GeneratorException("Error while spreading rooms: " + ex.getMessage());
        }
    }

    private void spread() throws GeneratorException {
        List<Room> shuffledRooms = randomGenerator.getShuffledList(dungeon.getRooms());
        List<Room> roomsWithFinishedMovements = new ArrayList<>();
        for (Room room : shuffledRooms) {
            int angle = randomGenerator.randomInt(0, 360);
            boolean intersects = true;
            while (intersects) {
                intersects = false;
                for (Room room2 : shuffledRooms) {
                    if (room == room2)
                        continue;
                    if (!roomsWithFinishedMovements.contains(room2)) // check intersection only with rooms that has done their movements to avoid gaps in the middle from large rooms that moved too late
                        continue;
                    double extraOffset = randomGenerator.randomDouble(0.0, 0.1);
                    if (room.intersects(room2, extraOffset)) {
                        Point<Integer> difference = getDifferenceInGivenAngle(room, room2, angle + randomGenerator.randomInt(-40, 40), extraOffset);
                        room.setPosition(new Point<>(
                                room.getPosition().getX() + difference.getX(),
                                room.getPosition().getY() + difference.getY()
                        ));
                        intersects = true;
                        break;
                    }
                }
            }
            roomsWithFinishedMovements.add(room);
        }
    }

    /**
     * Returns vector, in which referenceRoom should be shifted to not overlap with anotherRoom.
     * Returned vector is in direction of given angle.
     * </p>
     * <b>Make sure that checked rooms really intersects</b>
     * @param referenceRoom Reference room that would need to be shifted
     * @param anotherRoom Another room to check intersection against
     * @param angle Angle in degrees of direction to move referenceRoom in
     * @param extraOffset Extra offset of referenceRoom as part of its base size (1.0 = base size, 0.5 = half of base size)
     * @return Escape vector
     */
    private Point<Integer> getDifferenceInGivenAngle(Room referenceRoom, Room anotherRoom, double angle, double extraOffset) {
        angle = angle % 360;
        if (angle < 0)
            angle += 360;
        int extraOffsetX = (int) (referenceRoom.getShape().getWidth() * extraOffset);
        int extraOffsetY = (int) (referenceRoom.getShape().getHeight() * extraOffset);
        double multiX = 1, multiY = 1; // Multiplier of shift, depends on relative direction between rooms
        int shiftX, shiftY;
        if (angle <= 90) { // Right top
            if (angle <= 45)
                multiX = (angle-0)/45.0;
            else
                multiY = (angle-45.0)/45.0;
            shiftX = anotherRoom.getTopRightCorner().getX() - (referenceRoom.getTopLeftCorner().getX()-extraOffsetX);
            shiftY = anotherRoom.getTopLeftCorner().getY() - (referenceRoom.getBottomLeftCorner().getY()+extraOffsetY);
        } else if (angle <= 180) { // Right bottom
            if (angle <= 135)
                multiY = (angle-90.0)/45.0;
            else
                multiX = (angle-135.0)/45.0;
            shiftX = anotherRoom.getTopRightCorner().getX() - (referenceRoom.getTopLeftCorner().getX()-extraOffsetX);
            shiftY = anotherRoom.getBottomLeftCorner().getY() - (referenceRoom.getTopLeftCorner().getY()-extraOffsetY);
        } else if (angle <= 270) { // Left bottom
            if (angle <= 225)
                multiX = (angle-180.0)/45.0;
            else
                multiY = (angle-225.0)/45.0;
            shiftX = anotherRoom.getTopLeftCorner().getX() - (referenceRoom.getTopRightCorner().getX()+extraOffsetX);
            shiftY = anotherRoom.getBottomLeftCorner().getY() - (referenceRoom.getTopLeftCorner().getY()-extraOffsetY);
        } else { // Left top
            if (angle <= 315)
                multiY = (angle-270.0)/45.0;
            else
                multiX = (angle-315.0)/45.0;
            shiftX = anotherRoom.getTopLeftCorner().getX() - (referenceRoom.getTopRightCorner().getX()+extraOffsetX);
            shiftY = anotherRoom.getTopLeftCorner().getY() - (referenceRoom.getBottomLeftCorner().getY()+extraOffsetY);
        }
        shiftX *= multiX;
        shiftY *= multiY;
        return new Point<>(shiftX, shiftY);
    }

}
