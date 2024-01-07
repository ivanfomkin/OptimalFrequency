package ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nntu.its.at.ifomkin.optimalfrequency.excetion.UnsupportedFileDataException;

import java.util.Arrays;

/**
 * Класс, реализующий работу с входными данными из однострочного текстового файла
 */

@Data
@Slf4j
@RequiredArgsConstructor
public class InlineTxtFileData implements InputData {
    private final String data;

    @Override
    public double[] getData() {
        String delimiter = getDelimiter(data);
        try {
            return Arrays.stream(data.split(delimiter))
                    .map(Double::parseDouble)
                    .mapToDouble(d -> d)
                    .toArray();
        } catch (Exception exception) {
            throw new UnsupportedFileDataException("Ошибка при преобразовании строки в число", exception);
        }

    }

    private String getDelimiter(String data) {
        if (data.contains(";")) {
            return ";";
        }
        if (data.contains(" ")) {
            return " ";
        }
        if (data.contains(",")) {
            return ",";
        }
        throw new UnsupportedFileDataException("Неизвестный разделитель в файле");
    }

}
