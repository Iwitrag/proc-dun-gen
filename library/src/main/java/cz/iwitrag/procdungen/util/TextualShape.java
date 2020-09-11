package cz.iwitrag.procdungen.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Shape defined by rows of chars
 */
public class TextualShape {

    /** Represents empty unused space */
    private final char EMPTY;
    /** Represents used space */
    private final char FILLED;
    /** Newline separator */
    private final char SEPARATOR;

    private List<String> shape;
    /** Current shape rotation */
    private int rotated = 0;

    /**
     * Creates copy of textual shape
     * @param textualShape Shape to copy from
     */
    public TextualShape(TextualShape textualShape) {
        this.EMPTY = textualShape.EMPTY;
        this.FILLED = textualShape.FILLED;
        this.SEPARATOR = textualShape.SEPARATOR;
        this.shape = new ArrayList<>(textualShape.shape);
        this.rotated = textualShape.rotated;
    }

    /**
     * Creates new textual shape
     * @param shape Shape input, lines must be separated by {{@link #SEPARATOR}}
     * @param empty Char representing empty space
     * @param filled Char representing used space
     * @param separator Char representing new line
     */
    public TextualShape(String shape, char empty, char filled, char separator) {
        if (empty == filled || empty == separator || filled == separator)
            throw new IllegalArgumentException("Chars for TextualShape must be different");
        this.EMPTY = empty;
        this.FILLED = filled;
        this.SEPARATOR = separator;
        setShape(shape);
    }

    public char getEMPTY() {
        return EMPTY;
    }

    public char getFILLED() {
        return FILLED;
    }

    public char getSEPARATOR() {
        return SEPARATOR;
    }

    public String getShape() {
        return StringUtils.join(shape, this.SEPARATOR);
    }

    public List<String> getShapeRows() {
        return new ArrayList<>(shape);
    }

    public String getShapeRow(int n) {
        if (n < 0 || n >= getHeight())
            throw new IndexOutOfBoundsException("Row is out of bounds");
        return shape.get(n);
    }

    /**
     * Sets shape from String and overwrites current shape<br>
     * Parameter must contain allowed chars set for this shape
     * @param shape Text shape, rows must be separated by {@link #SEPARATOR}
     */
    public void setShape(String shape) {
        if (shape == null)
            throw new IllegalArgumentException("Shape is null");
        shape = shape.trim();

        // Remove last separator
        if (!shape.isEmpty() && shape.charAt(shape.length()-1) == this.SEPARATOR)
            shape = shape.substring(0, shape.length()-1);

        // Check empty
        if (shape.isEmpty())
            throw new IllegalArgumentException("Shape is empty");
        shape = shape.toLowerCase();

        // Check characters
        String allowedCharsPattern = "[" + Pattern.quote(String.valueOf(new char[]{this.EMPTY, this.FILLED, this.SEPARATOR})) + "]*";
        if (!shape.matches(allowedCharsPattern)) {
            throw new IllegalArgumentException("Shape contains invalid characters, allowed regex is " + allowedCharsPattern);
        }

        // Convert String to lines and check equal length
        String[] split = shape.split(String.valueOf(this.SEPARATOR));
        List<String> newShape = new ArrayList<>();
        int shapeRowLength = split[0].length();
        for (String line : split) {
            if (shapeRowLength != line.length())
                throw new IllegalArgumentException("Shape contains rows of unequal length");
            newShape.add(line);
        }

        this.shape = newShape;
        this.rotated = 0;

        // Trim empty rows and columns on borders
        trim();

        // IWI TEXTSHAPE - check if one part, check if not hollow
    }

    /**
     * Returns info about rotation of this shape
     * @return Rotation of this shape (1 = 90 degrees clock-wise, 2 = 180 degrees, ...)
     */
    public int getRotated() {
        return rotated;
    }

    private void trim() {
        String emptyRowPattern = "[" + Pattern.quote(String.valueOf(this.EMPTY)) + "]*";
        List<String> newShape = new ArrayList<>(this.shape);

        // Trim shape from the top
        while (!newShape.isEmpty() && newShape.get(0).matches(emptyRowPattern))
            newShape.remove(0);

        // Trim shape from the bottom
        while (!newShape.isEmpty() && newShape.get(newShape.size()-1).matches(emptyRowPattern))
            newShape.remove(newShape.size()-1);

        // Trim shape from the left
        while (!newShape.isEmpty() && newShape.stream().allMatch((row) -> row.charAt(0) == this.EMPTY)) {
            for (int i = 0; i < newShape.size(); i++) {
                String row = newShape.get(i);
                if (row.length() > 1)
                    newShape.set(i, row.substring(1));
                else
                    newShape.remove(i--);
            }
        }

        // Trim shape from the right
        while (!newShape.isEmpty() && newShape.stream().allMatch((row) -> row.charAt(row.length()-1) == this.EMPTY)) {
            for (int i = 0; i < newShape.size(); i++) {
                String row = newShape.get(i);
                if (row.length() > 1)
                    newShape.set(i, row.substring(0, row.length()-1));
                else
                    newShape.remove(i--);
            }
        }

        this.shape = newShape;
    }

    /**
     * Rotates this shape by 90 degree steps in clock-wise direction
     * @param clockwiseSteps How many steps to do (4 = does nothing, 5 = same as 1)
     */
    public void rotate(int clockwiseSteps) {
        clockwiseSteps = clockwiseSteps % 4;
        if (clockwiseSteps == 0)
            return;
        if (clockwiseSteps < 0)
            clockwiseSteps += 4;
        rotate(clockwiseSteps-1);

        // Rotate by 90°
        int width = getWidth();
        int height = getHeight();
        this.rotated = (this.rotated+1) % 4;

        // Prepare builders to create rows
        StringBuilder[] rowBuilders = new StringBuilder[width];
        for (int x = 0; x < width; x++) {
            rowBuilders[x] = new StringBuilder();
        }

        // Transpose (switch X and Y) and reverse columns order
        for (int y = 0; y < height; y++) {
            String row = shape.get(y);
            for (int x = width - 1; x >= 0; x--) {
                char c = row.charAt(x);
                rowBuilders[x].append(c);
            }
        }

        // Build rows
        List<String> newShape = new ArrayList<>();
        for (int i = width - 1; i >= 0; i--) {
            newShape.add(rowBuilders[i].toString());
        }
        this.shape = newShape;
    }

    /**
     * Returns number of chars in a row
     * @return Number of chars in a row
     */
    public int getWidth() {
        return shape.get(0).length();
    }

    /**
     * Returns number of rows in this shape
     * @return Number of rows
     */
    public int getHeight() {
        return shape.size();
    }

    /**
     * Returns char at specific matrix coordinates
     * @param x X coordinate (position in row starting from 0)
     * @param y Y coordinate (row number starting from 0)
     * @return Empty or filled char
     * @throws IndexOutOfBoundsException When given coordinates are out of bounds
     */
    public char getCharAt(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())
            throw new IndexOutOfBoundsException("Coordinates are out of bounds");
        return shape.get(y).charAt(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextualShape)) return false;
        TextualShape textShape = (TextualShape) o;
        return Objects.equals(getShape(), textShape.getShape());
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape);
    }

    @Override
    public String toString() {
        return getShape();
    }
}
