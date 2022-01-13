package com.tuany;

/**
 * Contains public static functions for random number generation.
 * @author Tuany Van
 * @version 1.0, 01/12/22
 */

public class Random {
    // Declare private function that returns a random number within an inclusive range.
    public static int getRandomInt(int lowerBound, int upperBound) {
        return (int)(Math.random() * (upperBound + 1 - lowerBound) + lowerBound);
    }

    // Declare an overloaded private function that returns a random number from 0 to an inclusive upper bound.
    public static int getRandomInt(int upperBound) {
        return (int)(Math.random() * (upperBound + 1));
    }

}
