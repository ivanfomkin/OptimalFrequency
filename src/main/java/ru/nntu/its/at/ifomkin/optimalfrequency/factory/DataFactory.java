package ru.nntu.its.at.ifomkin.optimalfrequency.factory;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.nntu.its.at.ifomkin.optimalfrequency.exception.UnsupportedFileDataException;
import ru.nntu.its.at.ifomkin.optimalfrequency.exception.UnsupportedFileTypeException;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.InlineTxtFileData;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.InputData;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.MultilineTxtFileData;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.FileUtil;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class DataFactory {

    public InputData getData(MultipartFile file) {
        var fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return switch (Objects.requireNonNull(fileExtension, "Не удалось определить расширение файла")) {
            case "txt", "csv" -> getTxtData(file);
            default -> throw new UnsupportedFileTypeException(fileExtension);
        };
    }

    private InputData getTxtData(MultipartFile file) {
        String stringData = FileUtil.getMultipartStringContent(file);
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
