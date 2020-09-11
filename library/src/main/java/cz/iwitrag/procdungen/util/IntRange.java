package cz.iwitrag.procdungen.util;

import java.util.Objects;

/**
 * Represents integer range from-to
 */
public class IntRange {

    private int min;
    private int max;

    /** Creates new integer range with both values exactly the same */
    public IntRange(int minMax) {
        this.min = minMax;
        this.max = minMax;
    }

    /** Creates new integer range with min and max values
     * @throws IllegalArgumentException If min is bigger than max */
    public IntRange(int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("Min value cannot be bigger than Max value");
        this.min = min;
        this.max = max;
    }

    /**
     * Creates new integer range from String formatted as XsepY, where X is min, Y is max and sep is separator<br>
     * Example: 5-10, 5 is min, 10 is max, - is separator
     * @param range String representation
     * @param separator Separator to use for parsing
     */
    public static IntRange fromString(String range, String separator) {
        String min, max;
        if (separator != null && range.contains(separator)) {
            String[] split = range.split(separator);
            min = split[0];
            max = split[1];
        } else {
            min = max = range;
        }
        try {
            return new IntRange(Integer.parseInt(min), Integer.parseInt(max));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid value provided");
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    /**
     * Returns random number from this range, min and max inclusive
     * @return Random int from given range
     */
    public int getRandom() {
        return new RandomGenerator().randomInt(min, max+1);
    }

    /**
     * Returns random number from this range, min and max inclusive, and uses provided random generator
     * @param randomGenerator Random generator to use
     * @return Random int from given range
     */
    public int getRandom(RandomGenerator randomGenerator) {
        return randomGenerator.randomInt(min, max+1);
    }

    @Override
    public String toString() {
        if (min == max)
            return String.valueOf(min);
        else
            return min + " - " + max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntRange intRange = (IntRange) o;
        return min == intRange.min &&
                max == intRange.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
