package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.data.*;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.RandomGenerator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Creates corridors between connected rooms<br>
 * In some cases it may produce intersections with other corridors or rooms, be cautious
 */
public class CorridorMaker {

    private Dungeon dungeon;
    private int freeCorridorId = 0;

    public CorridorMaker(Dungeon dungeon, RandomGenerator randomGenerator) {
        this.dungeon = dungeon;
    }

    public void createCorridors() throws GeneratorException {
        try {
            makeCorridors();
        } catch (Exception ex) {
            throw new GeneratorException("Error while making corridors: " + ex.getMessage());
        }
    }

    private void makeCorridors() throws GeneratorException {
        Set<Room> processedRooms = new LinkedHashSet<>();
        for (Room room : dungeon.getRooms()) {
            for (Room room2 : room.getConnectedRooms()) {
                if (!processedRooms.contains(room2)) {
                    createCorridor(room, room2);
                }
            }
            processedRooms.add(room);
        }
    }

    // For future reference when new algorithm is about to be implemented:
    // IWI even straight corridors may collide (seed 1649519705747637226), do more permutations
    // IWI 395439920871980849 collision L shape
    // IWI 4224354721586225860 - wrong choose of L corridor, it collides but undetected
    // IWI 1649519705747637226 - collision of straight corridors (undetected)

    private void createCorridor(Room room1, Room room2) throws GeneratorException {
        if (room1.equals(room2))
            throw new GeneratorException("Tried to create corridor between room " + room1.getId() + " itself");

        int r1x1 = room1.getTopLeftCorner().getX();
        int r1x2 = room1.getTopRightCorner().getX();
        int r1y1 = room1.getTopLeftCorner().getY();
        int r1y2 = room1.getBottomLeftCorner().getY();
        int r2x1 = room2.getTopLeftCorner().getX();
        int r2x2 = room2.getTopRightCorner().getX();
        int r2y1 = room2.getTopLeftCorner().getY();
        int r2y2 = room2.getBottomLeftCorner().getY();

        Corridor result;

        // Calculate overlap ranges on X and Y axis
        int rangeX1 = Math.max(r1x1, r2x1);
        int rangeX2 = Math.min(r1x2, r2x2);
        int rangeY1 = Math.max(r1y1, r2y1);
        int rangeY2 = Math.min(r1y2, r2y2);

        Point<Integer> start;
        Point<Integer> end;
        // Rooms overlap on X axis
        if (rangeX1 < rangeX2) {
            // Get corridor X position
            int corridorX = rangeX1 + ((rangeX2-rangeX1)/2);
            // Find which two Y edges are closest
            if (r1y2 <= r2y1) {
                // If rooms are connected by shape, dont create corridor
                if (Math.abs(r1y2-r2y1) == 0) {
                    for (int x = rangeX1; x < rangeX2; x++) {
                        if ((dungeon.getRoomAtPosition(x, r1y2 - 1, true) != null
                                && dungeon.getRoomAtPosition(x, r2y1, true) != null)) {
                            return;
                        }
                    }
                }
                start = new Point<>(corridorX, r1y2);
                end = new Point<>(corridorX, r2y1-1);
            } else {
                // If rooms are connected by shape, dont create corridor
                if (Math.abs(r1y1 - r2y2) == 0) {
                    for (int x = rangeX1; x < rangeX2; x++) {
                        if ((dungeon.getRoomAtPosition(x, r1y1, true) != null
                                && dungeon.getRoomAtPosition(x, r2y2 - 1, true) != null)) {
                            return;
                        }
                    }
                }
                start = new Point<>(corridorX, r1y1);
                end = new Point<>(corridorX, r2y2-1);
            }
            // Extend start and end to exact room boundaries
            start = extendPointToRoom(start, Direction4.UP);
            end = extendPointToRoom(end, Direction4.DOWN);

            result = createDirectCorridor(start, end);
        }
        // Rooms overlap on Y axis
        else if (rangeY1 < rangeY2) {
            // Get corridor Y position
            int corridorY = rangeY1 + ((rangeY2-rangeY1)/2);
            // Find which two X edges are closest
            if (r1x2 <= r2x1) {
                // If rooms are connected by shape, dont create corridor
                if (Math.abs(r1x2-r2x1) == 0) {
                    for (int y = rangeY1; y < rangeY2; y++) {
                        if ((dungeon.getRoomAtPosition(r1x2 - 1, y, true) != null
                                && dungeon.getRoomAtPosition(r2x1, y, true) != null)) {
                            return;
                        }
                    }
                }
                start = new Point<>(r1x2, corridorY);
                end = new Point<>(r2x1-1, corridorY);
            } else {
                // If rooms are connected by shape, dont create corridor
                if (Math.abs(r1x1-r2x2) == 0) {
                    for (int y = rangeY1; y < rangeY2; y++) {
                        if ((dungeon.getRoomAtPosition(r1x1, y, true) != null
                                && dungeon.getRoomAtPosition(r2x2 - 1, y, true) != null)) {
                            return;
                        }
                    }
                }
                start = new Point<>(r1x1, corridorY);
                end = new Point<>(r2x2-1, corridorY);
            }
            // Extend start and end to exact room boundaries
            start = extendPointToRoom(start, Direction4.LEFT);
            end = extendPointToRoom(end, Direction4.RIGHT);

            result = createDirectCorridor(start, end);
        }
        // Rooms do not overlap
        else {
            Point<Integer> firstCorner, secondCorner, middlePoint;
            Room firstRoom, secondRoom;
            boolean r1TopLeft = r1x2-1 < r2x1 && r1y2-1 < r2y1;
            boolean r1BottomRight = r1x1 > r2x2-1 && r1y1 > r2y2-1;
            boolean r1BottomLeft = r1x2-1 < r2x1 && r1y1 > r2y2-1;
            boolean r1TopRight = r1x1 > r2x2-1 && r1y2-1 < r2y2-1;
            if (r1TopLeft || r1BottomRight) {
                firstRoom = r1TopLeft ? room1 : room2;
                secondRoom = r1TopLeft ? room2 : room1;

                firstCorner = firstRoom.getBottomRightCorner().getShifted(0, -1);
                firstCorner = extendPointToRoom(firstCorner, Direction4.LEFT);
                secondCorner = secondRoom.getTopLeftCorner().getShifted(0, -1);
                secondCorner = extendPointToRoom(secondCorner, Direction4.DOWN);
                middlePoint = new Point<>(secondCorner.getX(), firstCorner.getY());
                result = mergeCorridors(createDirectCorridor(firstCorner, middlePoint), createDirectCorridor(middlePoint, secondCorner));

                // Try alternative L-connection if first try collided
                DungeonRectangle collisionObject = getCollidingObject(result);
                if (collisionObject != null && collisionObject != room1 && collisionObject != room2) {
                    firstCorner = firstRoom.getBottomRightCorner().getShifted(-1, 0);
                    firstCorner = extendPointToRoom(firstCorner, Direction4.UP);
                    secondCorner = secondRoom.getTopLeftCorner().getShifted(-1, 0);
                    secondCorner = extendPointToRoom(secondCorner, Direction4.RIGHT);
                    middlePoint = new Point<>(firstCorner.getX(), secondCorner.getY());
                    result = mergeCorridors(createDirectCorridor(firstCorner, middlePoint), createDirectCorridor(middlePoint, secondCorner));
                }
            }
            else {
                firstRoom = r1BottomLeft ? room1 : room2;
                secondRoom = r1BottomLeft ? room2 : room1;

                firstCorner = firstRoom.getTopRightCorner();
                firstCorner = extendPointToRoom(firstCorner, Direction4.LEFT);
                secondCorner = secondRoom.getBottomLeftCorner();
                secondCorner = extendPointToRoom(secondCorner, Direction4.UP);
                middlePoint = new Point<>(secondCorner.getX(), firstCorner.getY());
                result = mergeCorridors(createDirectCorridor(firstCorner, middlePoint), createDirectCorridor(middlePoint, secondCorner));

                // Try alternative L-connection if first try collided
                DungeonRectangle collisionObject = getCollidingObject(result);
                if (collisionObject != null && collisionObject != room1 && collisionObject != room2) {
                    firstCorner = firstRoom.getTopRightCorner().getShifted(-1, -1);
                    firstCorner = extendPointToRoom(firstCorner, Direction4.DOWN);
                    secondCorner = secondRoom.getBottomLeftCorner().getShifted(-1, -1);
                    secondCorner = extendPointToRoom(secondCorner, Direction4.RIGHT);
                    middlePoint = new Point<>(firstCorner.getX(), secondCorner.getY());
                    result = mergeCorridors(createDirectCorridor(firstCorner, middlePoint), createDirectCorridor(middlePoint, secondCorner));
                }
            }
        }

        DungeonRectangle collisionObject = getCollidingObject(result);
        if (collisionObject != null && collisionObject != room1 && collisionObject != room2) {
            // this check is disabled until fixed to not stop entire process of generation
            // throw new GeneratorException("Corridor between " + room1 + " and " + room2 + " causes collision with " + collisionObject);
        }

        if (result.getShape().getWidth() == 0 || result.getShape().getHeight() == 0)
            return;

        result.addConnectedRoom(room1);
        result.addConnectedRoom(room2);
        dungeon.addCorridor(result);
        freeCorridorId++;
    }

    private Corridor createDirectCorridor(Point<Integer> p1, Point<Integer> p2) {
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();
        Corridor result;
        // Corridor moving down (from p1 to p2 or from p2 to p1)
        if (x1 == x2) {
            Point<Integer> start = y1 > y2 ? p2 : p1;
            Point<Integer> end = y1 > y2 ? p1 : p2;
            result = new Corridor(freeCorridorId, start);
            int distance = Math.abs(start.getY()-end.getY());
            for (int i = 0; i <= distance; i++) {
                result.getShape().setGridCell(0, i, new GridCell(0, i, GridCell.Type.CORRIDOR));
            }
        }
        // Corridor moving right (from p1 to p2 or from p2 to p1)
        else if (y1 == y2) {
            Point<Integer> start = x1 > x2 ? p2 : p1;
            Point<Integer> end = x1 > x2 ? p1 : p2;
            result = new Corridor(freeCorridorId, start);
            int distance = Math.abs(start.getX()-end.getX());
            for (int i = 0; i <= distance; i++) {
                result.getShape().setGridCell(i, 0, new GridCell(i, 0, GridCell.Type.CORRIDOR));
            }
        }
        else
            throw new IllegalArgumentException("Cannot generate direct corridor, points dont have one equal coordinate (X or Y)");
        return result;
    }

    private DungeonRectangle getCollidingObject(DungeonRectangle rectangle) {
        for (Room room : dungeon.getRooms()) {
            if (rectangle == room)
                continue;
            if (rectangle.intersects(room))
                return room;
        }
        for (Corridor corridor : dungeon.getCorridors()) {
            if (rectangle == corridor)
                continue;
            if (rectangle.intersects(corridor))
                return corridor;
        }
        return null;
    }

    private Point<Integer> extendPointToRoom(Point<Integer> point, Direction4 direction) {
        int xShift = 0;
        int yShift = 0;
        while (dungeon.getRoomAtPosition(point.getX() + xShift + direction.x, point.getY() + yShift + direction.y, true) == null) {
            xShift += direction.x;
            yShift += direction.y;
        }
        return point.getShifted(xShift, yShift);
    }

    private Corridor mergeCorridors(Corridor corridor1, Corridor corridor2) {
        int minX = Math.min(corridor1.getPosition().getX(), corridor2.getPosition().getX());
        int minY = Math.min(corridor1.getPosition().getY(), corridor2.getPosition().getY());
        Corridor result = new Corridor(freeCorridorId, minX, minY);

        for (GridCell gridCell : corridor1.getShape()) {
            int newGridCellPosX = gridCell.getPosition().getX() + Math.abs(minX - corridor1.getPosition().getX());
            int newGridCellPosY = gridCell.getPosition().getY() + Math.abs(minY - corridor1.getPosition().getY());
            GridCell newGridCell = new GridCell(newGridCellPosX, newGridCellPosY, GridCell.Type.CORRIDOR);
            newGridCell.setCorridor(result);
            result.getShape().setGridCell(newGridCellPosX, newGridCellPosY, newGridCell);
        }
        for (GridCell gridCell : corridor2.getShape()) {
            int newGridCellPosX = gridCell.getPosition().getX() + Math.abs(minX - corridor2.getPosition().getX());
            int newGridCellPosY = gridCell.getPosition().getY() + Math.abs(minY - corridor2.getPosition().getY());
            GridCell newGridCell = new GridCell(newGridCellPosX, newGridCellPosY, GridCell.Type.CORRIDOR);
            newGridCell.setCorridor(result);
            result.getShape().setGridCell(newGridCellPosX, newGridCellPosY, newGridCell);
        }
        for (Room room : corridor1.getConnectedRooms()) {
            result.addConnectedRoom(room);
        }
        for (Room room : corridor2.getConnectedRooms()) {
            result.addConnectedRoom(room);
        }
        return result;
    }

    private enum Direction4 {
        LEFT, DOWN, RIGHT, UP;
        private int x;
        private int y;
        static {
            LEFT.x = -1;
            LEFT.y = 0;
            DOWN.x = 0;
            DOWN.y = 1;
            RIGHT.x = 1;
            RIGHT.y = 0;
            UP.x = 0;
            UP.y = -1;
        }
        public int x() {
            return x;
        }
        public int y() {
            return y;
        }
    }
}
