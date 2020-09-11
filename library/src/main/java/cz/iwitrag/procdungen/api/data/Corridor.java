package cz.iwitrag.procdungen.api.data;

import cz.iwitrag.procdungen.util.TextualShape;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Corridor extends DungeonRectangle {

    /** Generated unique id */
    int id;
    /** List of rooms this corridor connects */
    private Set<Room> connectedRooms = new LinkedHashSet<>();

    public Corridor(int id, Point<Integer> position) {
        this.id = id;
        this.position = position;
        this.shape = new Grid();
    }

    public Corridor(int id, int x, int y) {
        this.id = id;
        this.position = new Point<>(x, y);
        this.shape = new Grid();
    }

    public Corridor(int id, Point<Integer> position, TextualShape shape) {
        this.id = id;
        this.position = position;
        this.shape = new Grid(shape, GridCell.Type.CORRIDOR);
    }

    public Corridor(int id, int x, int y, TextualShape shape) {
        this.id = id;
        this.position = new Point<>(x, y);
        this.shape = new Grid(shape, GridCell.Type.CORRIDOR);
    }

    public int getId() {
        return id;
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

    public void addConnectedRoom(Room room) {
        connectedRooms.add(room);
    }

    public void removeConnectedRoom(Room room) {
        connectedRooms.remove(room);
    }

    @Override
    public String toString() {
        return "CORRIDOR ID: " + id + ", top-left: " + getTopLeftCorner() + ", bottom-right: " + getBottomRightCorner();
    }

}
