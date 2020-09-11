package cz.iwitrag.procdungen.api.configuration;

import cz.iwitrag.procdungen.util.IntRange;

/**
 * Room in a dungeon.<br>
 * This is general definition which can represent more generated rooms in final dungeon.<br>
 * Each Room must be represented by name (will be used in output), pre-created shape,
 * must specify amount of occurences in final dungeon and whether it's shape can be rotated.
 */
public class Room {

    /** Unique room name */
    private String name;
    /** Shape to be used */
    private RoomShape shape;
    /** Required amount of rooms to generate, using 0 in lower bound makes this room optional */
    private IntRange amount;
    /** Whether shape of this room may be randomly rotated */
    private boolean rotate = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomShape getShape() {
        return shape;
    }

    public void setShape(RoomShape shape) {
        this.shape = shape;
    }

    public IntRange getAmount() {
        return amount;
    }

    public void setAmount(IntRange amount) {
        this.amount = amount;
    }

    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", shape=" + shape +
                ", amount=" + amount +
                ", rotate=" + rotate +
                '}';
    }
}
