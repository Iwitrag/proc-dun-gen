package cz.iwitrag.procdungen.generator.templates;

import cz.iwitrag.procdungen.util.IntRange;
import cz.iwitrag.procdungen.util.RandomGenerator;
import cz.iwitrag.procdungen.util.TextualShape;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Square template defined by its side size
 */
public class SquareTemplate implements ParametrizedTemplate {

    private IntRange size;

    public SquareTemplate(IntRange size) {
        setSize(size);
    }

    public IntRange getSize() {
        return size;
    }

    public void setSize(IntRange size) {
        this.size = Objects.requireNonNull(size, "Parameter \"size\" must be specified");
        if (size.getMin() < 3)
            throw new IllegalArgumentException("Parameter \"size\" cannot be smaller than 3");
    }

    @Override
    public List<String> getParamNames() {
        return Collections.singletonList("size");
    }

    @Override
    public TextualShape createShape() {
        return createShape(this.size.getRandom());
    }

    @Override
    public TextualShape createShape(RandomGenerator randomGenerator) {
        return createShape(this.size.getRandom(randomGenerator));
    }

    private TextualShape createShape(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append('x');
        }
        String row = builder.toString();
        for (int i = 1; i < size; i++) {
            builder.append("\n").append(row);
        }
        return new TextualShape(builder.toString(), '.', 'x', '\n');
    }
}
