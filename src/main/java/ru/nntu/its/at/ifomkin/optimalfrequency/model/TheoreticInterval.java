package ru.nntu.its.at.ifomkin.optimalfrequency.model;

/**
 * @param middleValue                       Середина интервала
 * @param functionValue                     Значение функции f(x)
 * @param eventsCount                       Число событий в интервале
 * @param frequency                         Частость
 * @param probabilityEstimation             Оценка вероятности отказа
 * @param probabilityOfFailureFreeOperation Оценка вероятности безотказной работы
 * @param difference                        Разность (m' - m)^2 / m
 */
public record TheoreticInterval(double middleValue,
                                double functionValue,
                                double eventsCount,
                                double frequency,
                                double probabilityEstimation,
                                double probabilityOfFailureFreeOperation,
                                double difference
) {
}
