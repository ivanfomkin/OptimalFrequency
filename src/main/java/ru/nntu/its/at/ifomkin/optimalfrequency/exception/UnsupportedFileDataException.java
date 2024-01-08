package ru.nntu.its.at.ifomkin.optimalfrequency.exception;

public class UnsupportedFileDataException extends OptimalFrequencyException {
    public UnsupportedFileDataException(String message) {
        super(message);
    }

    public UnsupportedFileDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
