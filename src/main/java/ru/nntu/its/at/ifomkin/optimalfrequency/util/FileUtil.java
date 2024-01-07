package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@UtilityClass
public class FileUtil {
    public String getMultipartStringContent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }
        try {
            return new String(file.getBytes());
        } catch (IOException e) {
            log.warn("Не могу получить содержимое multipart-файла", e);
            return "";
        }
    }
}
