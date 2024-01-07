package ru.nntu.its.at.ifomkin.optimalfrequency.factory;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unbescape.csv.CsvEscape;
import ru.nntu.its.at.ifomkin.optimalfrequency.excetion.UnsupportedFileDataException;
import ru.nntu.its.at.ifomkin.optimalfrequency.excetion.UnsupportedFileTypeException;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.InlineTxtFileData;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.InputData;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.MultilineTxtFileData;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DataFactory {

    public InputData getData(MultipartFile file) {
        var fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return switch (fileExtension) {
            case "txt", "csv" -> getDataTxtData(file);
            default -> throw new UnsupportedFileTypeException(fileExtension);
        };
    }

    private InputData getDataTxtData(MultipartFile file) {
        String stringData;
        try {
            stringData = new String(file.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        String[] fileLines = stringData.split("\n");
        if (fileLines.length > 1) {
            return new MultilineTxtFileData(Arrays.asList(fileLines));
        } else if (fileLines.length == 1) {
            return new InlineTxtFileData(fileLines[0]);
        } else {
            throw new UnsupportedFileDataException("Некорректный формат текстового файла");
        }
    }
}
