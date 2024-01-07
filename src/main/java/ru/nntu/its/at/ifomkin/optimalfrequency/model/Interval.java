package ru.nntu.its.at.ifomkin.optimalfrequency.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Interval {
    private final double[] values; //Значения в интервале
    private final double intervalSize; //Количество значений в интервале
    private final double startValue; // Нижняя граница
    private final double endValue; //Верхняя граница
    private final double middleValue; //Середина интервала
    private IntervalStats intervalStats; //Статистическая обработка данных
}
