package ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс, реализующий работу с входными данными из многострочного текстового файла
 */
@Data
@RequiredArgsConstructor
public class MultilineTxtFileData implements InputData {
    private final List<String> data;

    @Override
    public double[] getData() {
        return data.stream().mapToDouble(Double::parseDouble).toArray();
    }
}