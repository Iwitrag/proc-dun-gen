package cz.iwitrag.procdungen.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Class to work with randomness based on common seed
 */
public class RandomGenerator {

    /** Seed to work with */
    private long seed;
    /** Back-end */
    private Random random;

    /**
     * Creates new generator with randomly picked seed
     */
    public RandomGenerator() {
        initRandom();
    }

    /** Creates new generator with seed as long number */
    public RandomGenerator(long seed) {
        initRandom(seed);
    }

    /** Creates new generator with seed converted to long number */
    public RandomGenerator(String seed) {
        if (seed == null || seed.isEmpty())
            initRandom();
        else
            initRandom(seed);
    }

    private void initRandom() {
        this.random = new Random();
        this.seed = random.nextLong();
        this.random.setSeed(this.seed);
    }

    private void initRandom(long seed) {
        this.random = new Random();
        this.seed = random.nextLong();
        this.seed = seed;
        this.random.setSeed(this.seed);
    }

    private void initRandom(String seed) {
        this.random = new Random();
        this.seed = random.nextLong();
        // Source: https://stackoverflow.com/a/46095268/2872536 , author: https://stackoverflow.com/users/3199008/ran
        this.seed = UUID.nameUUIDFromBytes(seed.getBytes()).getMostSignificantBits();
        this.random.setSeed(this.seed);
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Picks random integer from given range
     * @param from Lower bound (inclusive)
     * @param to Higher bound (exclusive)
     * @return Pseudorandom integer from given range
     * @throws IllegalArgumentException When "from" is not smaller than "to"
     */
    public int randomInt(int from, int to) {
        if (from >= to)
            throw new IllegalArgumentException("From (" + from + ") must be smaller than To (" + to + ")");
        return from + this.random.nextInt(to-from);
    }

    /**
     * Picks random double from given range
     * @param from Lower bound (inclusive)
     * @param to Higher bound (exclusive)
     * @return Pseudorandom double from given range
     * @throws IllegalArgumentException When "from" is not smaller than "to"
     */
    public double randomDouble(double from, double to) {
        if (from >= to)
            throw new IllegalArgumentException("From (" + from + ") must be smaller than To (" + to + ")");
        return from + ((to-from) * this.random.nextDouble());
    }

    /**
     * Picks random element from given collection
     * @param collection Collection of elements
     * @param <T> Type of elements
     * @return Random element, null if collection was empty
     */
    public <T> T pickRandomElement(Collection<T> collection) {
        int randomInt = randomInt(0, collection.size());
        int i = 0;
        for (T item : collection) {
            if (i++ == randomInt)
                return item;
        }
        return null;
    }

    /**
     * Creates new list with shuffled elements from collection
     * @param collection Collection of elements (remains intact)
     * @param <T> Type of elements
     * @return List with shuffled elements,
     */
    public <T> List<T> getShuffledList(Collection<T> collection) {
        List<T> source = new ArrayList<>(collection);
        List<T> shuffled = new ArrayList<>();
        while (!source.isEmpty()) {
            T item = pickRandomElement(source);
            shuffled.add(item);
            source.remove(item);
        }
        return shuffled;
    }

    /**
     * Returns true or false based on chance
     * @param percent Chance from 0.0 to 100.0
     * @return Whether random event with given percentage happened
     */
    public boolean chance(double percent) {
        return this.random.nextDouble() < percent/100;
    }

}