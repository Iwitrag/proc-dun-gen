package cz.iwitrag.procdungen.api.data;

import java.util.Objects;

/**
 * Represents one cell in a grid<br>
 * Used for dungeons and room+corridor shapes
 */
public class GridCell {

    /** Relative position of this cell in a grid */
    private final Point<Integer> position;
    /** Type of this cell */
    private Type type;
    /** Room this cell represents */
    private Room room;
    /** Corridor this cell represents */
    private Corridor corridor;

    public GridCell(Point<Integer> point, Type type) {
        this.position = point;
        this.type = type;
    }

    public GridCell(int x, int y, Type type) {
        this.position = new Point<>(x, y);
        this.type = type;
    }

    public Point<Integer> getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Corridor getCorridor() {
        return corridor;
    }

    public void setCorridor(Corridor corridor) {
        this.corridor = corridor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridCell)) return false;
        GridCell gridCell = (GridCell) o;
        return Objects.equals(position, gridCell.position) &&
                type == gridCell.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type);
    }

    /**
     * Represents type of cell for easy identification
     */
    public enum Type {
        /** Non-used space */
        NONE,
        /** Space occupied by room */
        ROOM,
        /** Space occupied by corridor */
        CORRIDOR;
        public static Type valueOfIgnoreCase(String value) {
            for (Type type : Type.values()) {
                if (type.name().equalsIgnoreCase(value))
                    return type;
            }
            return null;
        }
    }

}
