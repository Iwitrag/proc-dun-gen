package cz.iwitrag.procdungen.api.data;

import java.util.Objects;

/**
 * Represents point on 2D plane
 * @param <T> Type of value for X and Y
 */
public class Point<T extends Number> {

    /** X coordinate (from left to right) */
    private final T x;
    /** Y coordinate (from top to bottom) */
    private final T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point<T> point) {
        this.x = point.x;
        this.y = point.y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    /**
     * Returns new point which is shifted by delta from this point
     * @param xDelta X shift, can be negative, zero or positive
     * @param yDelta Y shift, can be negative, zero or positive
     * @return Shifted point
     */
    public Point<Integer> getShifted(Integer xDelta, Integer yDelta) {
        return new Point<>(this.x.intValue() + xDelta, this.y.intValue() + yDelta);
    }
    /**
     * Returns new point which is shifted by delta from this point
     * @param xDelta X shift, can be negative, zero or positive
     * @param yDelta Y shift, can be negative, zero or positive
     * @return Shifted point
     */
    public Point<Long> getShifted(Long xDelta, Long yDelta) {
        return new Point<>(this.x.longValue() + xDelta, this.y.longValue() + yDelta);
    }
    /**
     * Returns new point which is shifted by delta from this point
     * @param xDelta X shift, can be negative, zero or positive
     * @param yDelta Y shift, can be negative, zero or positive
     * @return Shifted point
     */
    public Point<Float> getShifted(Float xDelta, Float yDelta) {
        return new Point<>(this.x.floatValue() + xDelta, this.y.floatValue() + yDelta);
    }
    /**
     * Returns new point which is shifted by delta from this point
     * @param xDelta X shift, can be negative, zero or positive
     * @param yDelta Y shift, can be negative, zero or positive
     * @return Shifted point
     */
    public Point<Double> getShifted(Double xDelta, Double yDelta) {
        return new Point<>(this.x.doubleValue() + xDelta, this.y.doubleValue() + yDelta);
    }

    /**
     * Returns distance between this point and another point
     * @param point Another point
     * @param squareRoot Whether to get exact, non-squared distance (low performance)
     * @return Distance between two points
     */
    public double getDistance2D(Point<T> point, boolean squareRoot) {
        double xd = Math.pow(Math.abs(this.x.doubleValue() - point.x.doubleValue()), 2.0);
        double yd = Math.pow(Math.abs(this.y.doubleValue() - point.y.doubleValue()), 2.0);
        return squareRoot ? Math.sqrt(xd+yd) : (xd+yd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point<?> point = (Point<?>) o;
        return Objects.equals(x, point.x) &&
                Objects.equals(y, point.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
