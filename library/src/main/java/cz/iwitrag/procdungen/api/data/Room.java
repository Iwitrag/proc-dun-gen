package cz.iwitrag.procdungen.api.data;

import cz.iwitrag.procdungen.util.TextualShape;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Room extends DungeonRectangle {

    /** Generated unique id */
    int id;
    /** Unique name as was provided in config */
    private final String name;
    /** Unique shape name as was provided in config */
    private String shapeName;
    /** Rooms connected with this room */
    private Set<Room> connectedRooms = new LinkedHashSet<>();

    public Room(int id, String name, Point<Integer> position, TextualShape shape) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.shape = new Grid(shape, GridCell.Type.ROOM);
    }

    public Room(int id, String name, int x, int y, TextualShape shape) {
        this.id = id;
        this.name = name;
        this.position = new Point<>(x, y);
        this.shape = new Grid(shape, GridCell.Type.ROOM);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public void setConnectedRooms(Set<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public Set<Room> getConnectedRooms() {
        return new LinkedHashSet<>(connectedRooms);
    }

    public int getConnectedRoomsSize() {
        return connectedRooms.size();
    }

    public boolean isConnected(Room room) {
        return connectedRooms.contains(room);
    }

    public void setConnectedRooms(Collection<Room> connectedRooms) {
        this.connectedRooms.clear();
        this.connectedRooms.addAll(connectedRooms);
    }

    public void clearConnectedRooms() {
        this.connectedRooms.clear();
    }

    public void addConnectedRoom(Room room, boolean bidirectional) {
        if (room == this)
            throw new IllegalArgumentException("Tried to connect Room with itself");
        connectedRooms.add(room);
        if (bidirectional)
            room.addConnectedRoom(this, false);
    }

    public void removeConnectedRoom(Room room, boolean bidirectional) {
        connectedRooms.remove(room);
        if (bidirectional)
            room.removeConnectedRoom(this, false);
    }

    @Override
    public String toString() {
        return "ROOM ID: " + id + ", top-left: " + getTopLeftCorner() + ", bottom-right: " + getBottomRightCorner();
    }
}
