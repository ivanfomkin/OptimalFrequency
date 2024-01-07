package ru.nntu.its.at.ifomkin.optimalfrequency.excetion;

public class UnsupportedFileTypeException extends RuntimeException {

    public UnsupportedFileTypeException(String fileExtension) {
        super("Неподдерживаемый тип файла: " + fileExtension);
    }
}
