package cz.iwitrag.procdungen.api.data;

/**
 * Represents rectangle placed on dungeon 2D plane
 */
public abstract class DungeonRectangle implements Rectangle {

    /** Shape of this rectangle */
    protected Grid shape;
    /** Top-left corner */
    protected Point<Integer> position;

    public Grid getShape() {
        return shape;
    }

    public void setShape(Grid shape) {
        this.shape = shape;
    }

    public Point<Integer> getPosition() {
        return position;
    }

    public void setPosition(Point<Integer> position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Point<>(x, y);
    }

    /** Returns top left corner of this rectangle */
    @Override
    public Point<Integer> getTopLeftCorner() {
        return position;
    }

    /** Returns top right corner of this rectangle
     * <b>Precision warning</b>: resulting coordinates are indeed true coordinates of that corner, but
     * when working with cells (especially in grids), value 1 should be subtracted from X coordinate */
    @Override
    public Point<Integer> getTopRightCorner() {
        return new Point<>(position.getX() + shape.getWidth(), position.getY());
    }

    /** Returns bottom left corner of this rectangle
     * <b>Precision warning</b>: resulting coordinates are indeed true coordinates of that corner, but
     * when working with cells (especially in grids), value 1 should be subtracted from Y coordinate */
    @Override
    public Point<Integer> getBottomLeftCorner() {
        return new Point<>(position.getX(), position.getY() + shape.getHeight());
    }

    /** Returns bottom right corner of this rectangle
     * <b>Precision warning</b>: resulting coordinates are indeed true coordinates of that corner, but
     * when working with cells (especially in grids), value 1 should be subtracted from X and Y coordinates */
    @Override
    public Point<Integer> getBottomRightCorner() {
        return new Point<>(position.getX() + shape.getWidth(), position.getY() + shape.getHeight());
    }

    /** Returns center of this rectangle */
    @Override
    public Point<Double> getCenter() {
        return new Point<>(position.getX() + (shape.getWidth()/2.00), position.getY() + (shape.getHeight()/2.00));
    }

    /**
     * Checks whether two rectangles intersects<br>
     * This is done by checking if one rectangle is on the left side of another<br>
     * or if one rectangle is above another (if so, they don't intersect)
     * @param other Other rectangle to check
     * @return Whether rectangles intersect (or if one is inside other)
     */
    public boolean intersects(Rectangle other) {
        return intersects(other, 0.0);
    }

    /**
     * Checks whether two rectangles intersects<br>
     * This is done by checking if one rectangle is on the left side of another<br>
     * or if one rectangle is above another (if so, they don't intersect)
     * @param other Other rectangle to check
     * @param extraOffset Extra offset of this rectangle as part of its base size (1.0 = base size, 0.5 = half of base size)
     * @return Whether rectangles intersect (or if one is inside other)
     */
    public boolean intersects(Rectangle other, double extraOffset) {
        if (this == other)
            throw new IllegalArgumentException("Cannot check intersection of rectangle with itself");
        int extraOffsetX = (int) (this.getShape().getWidth() * extraOffset);
        int extraOffsetY = (int) (this.getShape().getHeight() * extraOffset);
        // One rectangle is on the left of another
        if (this.getTopRightCorner().getX()+extraOffsetX <= other.getTopLeftCorner().getX()
                || other.getTopRightCorner().getX() <= this.getTopLeftCorner().getX()-extraOffsetX)
            return false;
        // One rectangle is above another
        if (this.getBottomLeftCorner().getY()+extraOffsetY <= other.getTopLeftCorner().getY()
                || other.getBottomLeftCorner().getY() <= this.getTopLeftCorner().getY()-extraOffsetY)
            return false;
        return true;
    }
}
