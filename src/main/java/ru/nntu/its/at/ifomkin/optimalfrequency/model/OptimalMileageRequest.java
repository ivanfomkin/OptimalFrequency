package ru.nntu.its.at.ifomkin.optimalfrequency.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Запрос на вычисление оптимальной периодичности операции
 *
 * @author ivan.fomkin
 */

@Data
public class OptimalMileageRequest {
    /**
     * Файл с входными данными
     */
    private MultipartFile file;
    /**
     * Допустимый уровень риска. Значение по-умолчанию: 0.1 (10%)
     */
    private double acceptableLevelOfFailureFreeOperation = 0.1;
    /**
     * Уровень значимости (для определения значения критерия Пирсона)
     */
    private double significanceLevel = 0.05;
}
