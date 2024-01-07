package ru.nntu.its.at.ifomkin.optimalfrequency.excetion;

public class UnsupportedFileDataException extends RuntimeException {
    public UnsupportedFileDataException(String message) {
        super(message);
    }

    public UnsupportedFileDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
