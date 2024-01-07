package ru.nntu.its.at.ifomkin.optimalfrequency.excetion;

public class OptimalFrequencyException extends RuntimeException {

    public OptimalFrequencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptimalFrequencyException(String message) {
        super(message);
    }
}
