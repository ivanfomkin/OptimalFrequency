package ru.nntu.its.at.ifomkin.optimalfrequency.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class Dataset {
    private List<Interval> actualIntervals;
    private List<TheoreticInterval> theoreticIntervals;
    private double averageFailureMileage; // Средняя наработка до отказа
    private double variance; // Дисперсия
    private double variationCoefficient; //Коэффициент вариации
    private double standardDeviation; //Среднеквадратическое отклонение
    private double optimalMileage;
    private NormalDistributionCriteria normalDistributionCriteria;
    private String histogram;
    private String functionGraph;

    @Data
    @Builder
    public static class NormalDistributionCriteria {
        private double pirsonValue;
        private double pirsonTheoreticValue;
        private boolean normalDistribution;
        private int numberOfDegreesFreedom;
    }
}
