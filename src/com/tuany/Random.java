package com.tuany;

/**
 * Contains public static functions for random number generation.
 * @author Tuany Van
 * @version 1.1, 01/12/22
 */

public class Random {

    /**
     * Returns a random number based on a lower and upper bound.
     * @param lowerBound The inclusive minimum boundary which a randomized number can reach.
     * @param upperBound The inclusive maximum boundary which a randomized number can reach.
     * @return A random integer from the lowerBound to the upperBound inclusively.
     */
    public static int getRandomInt(int lowerBound, int upperBound) {
        return (int)(Math.random() * (upperBound + 1 - lowerBound) + lowerBound);
    }

    /**
     * Returns a random number simply based on an upper bound.
     * @param upperBound The inclusive maximum boundary which a randomized number can reach.
     * @return A random integer from 0 to the upperBound inclusively.
     */
    public static int getRandomInt(int upperBound) {
        return (int)(Math.random() * (upperBound + 1));
    }

}
