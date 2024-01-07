package ru.nntu.its.at.ifomkin.optimalfrequency.model;

public record IntervalStats(
        double size, //Число отказов в интервале
        double elementCountSum,
        double frequency, //Частость
        double probabilityEstimation, //Оценка вероятности отказа
        double densityEstimation, //Оценка плотности вероятности отказа
        double probabilityOfFailureFreeOperation //Оценка вероятности безотказной работы
) {
}
