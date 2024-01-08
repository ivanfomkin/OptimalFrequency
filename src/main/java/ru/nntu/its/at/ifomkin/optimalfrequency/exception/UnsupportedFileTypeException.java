package ru.nntu.its.at.ifomkin.optimalfrequency.exception;

public class UnsupportedFileTypeException extends OptimalFrequencyException {

    public UnsupportedFileTypeException(String fileExtension) {
        super("Неподдерживаемый тип файла: " + fileExtension);
    }
}
