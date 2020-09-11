package cz.iwitrag.procdungen.api.data;

import cz.iwitrag.procdungen.util.TextualShape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Grid represents matrix of two dimensional points (cells)<br>
 * Is used as "map" of pixels in Dungeon and as shape "picture" of rooms and corridors<br>
 * Its size may change dynamically based on queries for cells
 */
public class Grid implements Iterable<GridCell> {

    /** Width of grid */
    private int width;
    /** Height of grid */
    private int height;

    /**
     * Content of this grid<br>
     * Each sublist is column of cells with given X<br>
     * Empty cells are internally null, but NONE when asked for
     */
    private List<List<GridCell>> cells;

    /**
     * Creates empty grid with zero initial width and height
     */
    public Grid() {
        this.width = 0;
        this.height = 0;
        initGrid();
    }

    /**
     * Creates empty grid
     * @param width Initial width
     * @param height Initial height
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        initGrid();
    }

    /**
     * Creates grid with cells based on provided textual shape
     * @param textualShape Textual shape to construct grid with
     * @param filledType How to represent filled (occupied) space
     */
    public Grid(TextualShape textualShape, GridCell.Type filledType) {
        Objects.requireNonNull(textualShape, "Cannot create Grid from null textualShape");

        this.width = textualShape.getWidth();
        this.height = textualShape.getHeight();
        initGrid();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                char c = textualShape.getCharAt(x, y);
                if (c == textualShape.getFILLED())
                    setGridCell(x, y, new GridCell(x, y, filledType));
                else
                    setGridCell(x, y, null);
            }
        }
    }

    /**
     * Returns textual shape constructed from this grid
     * @return Textual shape where "x" is occupied, "." is empty and "\n" is newline char
     */
    public TextualShape getTextualShape() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                GridCell cell = getGridCell(x, y);
                if (cell.getType() == GridCell.Type.ROOM || cell.getType() == GridCell.Type.CORRIDOR)
                    builder.append('x');
                else
                    builder.append('.');
            }
            builder.append('\n');
        }
        builder.setLength(builder.length()-1);
        return new TextualShape(builder.toString(), '.', 'x', '\n');
    }

    private void initGrid() {
        if (cells == null) {
            cells = new ArrayList<>();
        }

        // Trim x
        if (cells.size() > width) {
            for (int i = cells.size() - width; i != 0; i--) {
                cells.remove(cells.size()-1);
            }
        }

        // Iterate columns
        for (int x = 0; x < width; x++) {
            List<GridCell> column = null;
            if (cells.size() > x)
                column = cells.get(x);

            // Create new column
            if (column == null) {
                List<GridCell> newColumn = new ArrayList<>();
                cells.add(newColumn);
                for (int y = 0; y < height; y++) {
                    newColumn.add(null);
                }
            }
            else {
                // Trim y
                if (column.size() > height) {
                    for (int i = column.size() - height; i != 0; i--) {
                        column.remove(column.size()-1);
                    }
                }

                for (int y = 0; y < height; y++) {
                    // Extend column
                    if (column.size() <= y)
                        column.add(null);
                }
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        initGrid();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        initGrid();
    }

    /**
     * Returns cell at specified position
     * @param x X coordinate, must be zero or higher
     * @param y Y coordinate, must be zero or higher
     * @return GridCell at given position (never null)
     * @throws IllegalArgumentException When negative coordinates are provided
     */
    public GridCell getGridCell(int x, int y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinates cannot be negative, given [" + x + ", " + y + "]");
        if (x >= cells.size())
            return new GridCell(x, y, GridCell.Type.NONE);
        List<GridCell> column = cells.get(x);
        if (y >= column.size())
            return new GridCell(x, y, GridCell.Type.NONE);
        GridCell cell = column.get(y);
        if (cell == null)
            return new GridCell(x, y, GridCell.Type.NONE);
        else
            return cell;
    }

    /**
     * Returns all non-empty cells of this grid
     * @return List of non-empty cells
     */
    public List<GridCell> getNonEmptyCells() {
        List<GridCell> result = new ArrayList<>();
        for (GridCell cell : this) {
            if (cell.getType() != GridCell.Type.NONE)
                result.add(cell);
        }
        return result;
    }

    /**
     * Sets cell at specified position<br>
     * If coordinates exceeds grid dimensions, grid is resized
     * @param x X coordinate, must be zero or higher
     * @param y Y coordinate, must be zero or higher
     * @param cell Cell to set
     * @throws IllegalArgumentException When negative coordinates are provided
     */
    public void setGridCell(int x, int y, GridCell cell) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinates cannot be negative, given [" + x + ", " + y + "]");
        if (x >= width)
            setWidth(x+1);
        if (y >= height)
            setHeight(y+1);
        cells.get(x).set(y, cell);
    }

    /**
     * Returns iterator to iterate all grid cells of this grid (including empty cells)
     * @return Iterator of cells, from left to right first, from top to bottom second (row by row)
     */
    @Override
    public Iterator<GridCell> iterator() {
        return new Iterator<GridCell>() {

            private int x = -1;
            private int y = 0;

            @Override
            public boolean hasNext() {
                return x + 1 < width || y + 1 < height;
            }

            @Override
            public GridCell next() {
                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
                return getGridCell(x, y);
            }

            @Override
            public void remove() {
                setGridCell(x, y, null);
            }
        };
    }
}
