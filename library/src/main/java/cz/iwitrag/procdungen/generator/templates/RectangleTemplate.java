package cz.iwitrag.procdungen.generator.templates;

import cz.iwitrag.procdungen.util.IntRange;
import cz.iwitrag.procdungen.util.RandomGenerator;
import cz.iwitrag.procdungen.util.TextualShape;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Rectangular template defined by width and height
 */
public class RectangleTemplate implements ParametrizedTemplate {

    private IntRange sizeX;
    private IntRange sizeY;

    public RectangleTemplate(IntRange sizeX, IntRange sizeY) {
        setSizeX(sizeX);
        setSizeY(sizeY);
    }

    public IntRange getSizeX() {
        return sizeX;
    }

    public void setSizeX(IntRange sizeX) {
        this.sizeX = Objects.requireNonNull(sizeX, "Parameter \"size-x\" must be specified");
        if (sizeX.getMin() < 3)
            throw new IllegalArgumentException("Parameter \"size-x\" cannot be smaller than 3");
    }

    public IntRange getSizeY() {
        return sizeY;
    }

    public void setSizeY(IntRange sizeY) {
        this.sizeY = Objects.requireNonNull(sizeY, "Parameter \"size-y\" must be specified");
        if (sizeY.getMin() < 3)
            throw new IllegalArgumentException("Parameter \"size-y\" cannot be smaller than 3");
    }

    @Override
    public TextualShape createShape() {
        return createShape(this.sizeX.getRandom(), this.sizeY.getRandom());
    }

    @Override
    public TextualShape createShape(RandomGenerator randomGenerator) {
        return createShape(this.sizeX.getRandom(randomGenerator), this.sizeY.getRandom(randomGenerator));
    }

    @Override
    public List<String> getParamNames() {
        return Arrays.asList("size-x", "size-y");
    }

    private TextualShape createShape(int sizeX, int sizeY) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sizeX; i++) {
            builder.append('x');
        }
        String row = builder.toString();
        for (int i = 1; i < sizeY; i++) {
            builder.append("\n").append(row);
        }
        return new TextualShape(builder.toString(), '.', 'x', '\n');
    }
}
