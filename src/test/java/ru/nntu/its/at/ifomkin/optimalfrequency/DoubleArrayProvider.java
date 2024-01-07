package ru.nntu.its.at.ifomkin.optimalfrequency;

import java.util.random.RandomGenerator;

public interface DoubleArrayProvider {
    RandomGenerator random = RandomGenerator.getDefault();

    default double[] generateArray(int size) {
        return random.doubles(size).toArray();
    }

}
