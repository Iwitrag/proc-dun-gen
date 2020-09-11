package cz.iwitrag.procdungen.api.data;

/**
 * Represents rectangle defined by all 4 corners and center
 */
public interface Rectangle {
    Point<Integer> getTopLeftCorner();
    Point<Integer> getTopRightCorner();
    Point<Integer> getBottomLeftCorner();
    Point<Integer> getBottomRightCorner();
    Point<Double> getCenter();
}
