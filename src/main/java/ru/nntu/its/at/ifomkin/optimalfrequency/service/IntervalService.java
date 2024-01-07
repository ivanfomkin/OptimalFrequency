package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Dataset;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Interval;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.IntervalStats;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.TheoreticInterval;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.StandardFunctionUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IntervalService {
    /**
     * Метод разбиения массива данных на интервалы
     *
     * @param data           массив данных
     * @param intervalsCount число интервалов
     * @return интервалы данных
     */
    protected List<Interval> divideDataToIntervals(double[] data, int intervalsCount) {
        Arrays.sort(data);
        ArrayList<Interval> intervals = new ArrayList<>(intervalsCount);
        double intervalsLength = calculateIntervalLength(data, intervalsCount);
        DoubleSummaryStatistics doubleSummaryStatistics = Arrays.stream(data).summaryStatistics();
        double arrayMinValue = doubleSummaryStatistics.getMin();
        double arrayMaxValue = doubleSummaryStatistics.getMax();
        for (double d = arrayMinValue; d < arrayMaxValue; d += intervalsLength) {
            Interval interval = createInterval(data, d, intervalsLength);
            Interval previousInterval = intervals.isEmpty() ? null : intervals.getLast();
            interval.setIntervalStats(calculateIntervalStats(interval, previousInterval, data.length));
            intervals.add(interval);
        }
        return intervals;
    }

    /**
     * Метод создания интервала
     *
     * @param data            массив данных
     * @param intervalStart   нижняя граница интервала
     * @param intervalsLength верхняя граница интервала
     * @return новы интервал
     */
    private static Interval createInterval(double[] data, double intervalStart, double intervalsLength) {
        double intervalEnd = intervalStart + intervalsLength;
        double middleValue = (intervalStart + intervalEnd) / 2;
        double[] intervalValues = Arrays.stream(data).filter(el -> el >= intervalStart && el <= intervalEnd).toArray();
        return new Interval(intervalValues, intervalValues.length, intervalStart, intervalEnd, middleValue);
    }

    public int calculateIntervalsCount(double[] failureStatisticArray) {
        double naturalValue = Math.round(2 * Math.pow(failureStatisticArray.length, 0.25));
        return (int) Math.round(naturalValue);
    }

    public double calculateIntervalLength(double[] failureStatisticArray, long intervalsCount) {
        DoubleSummaryStatistics doubleSummaryStatistics = Arrays.stream(failureStatisticArray).summaryStatistics();
        double min = doubleSummaryStatistics.getMin();
        double max = doubleSummaryStatistics.getMax();
        return (max - min) / intervalsCount;
    }

    /**
     * @param interval интервал, для которого следует вычислить статистику
     * @param dataSize общий размер выборки отказов
     * @return статистические данные интервала
     */
    public IntervalStats calculateIntervalStats(Interval interval, Interval previousInterval, int dataSize) {
        int intervalSize = interval.getValues().length;
        double frequency = calculateFrequency(dataSize, intervalSize);
        double elementsCountSum = calculateElementsCountSum(interval, previousInterval);
        double probabilityEstimation = calculateProbabilityEstimation(elementsCountSum, dataSize);
        double probabilityOfFailureFreeOperation = calculateProbabilityOfFailureFreeOperation(probabilityEstimation);
        double densityEstimation = calculateDensityEstimation(interval, dataSize, elementsCountSum);
        return new IntervalStats(
                intervalSize,
                elementsCountSum,
                frequency,
                probabilityEstimation,
                densityEstimation,
                probabilityOfFailureFreeOperation
        );
    }

    private double calculateDensityEstimation(Interval interval, double dataSize, double elementsCountSum) {
        double intervalLength = interval.getEndValue() - interval.getStartValue();
        return elementsCountSum / (intervalLength * dataSize);
    }

    private double calculateProbabilityOfFailureFreeOperation(double probabilityEstimation) {
        return 1 - probabilityEstimation;
    }

    private double calculateProbabilityEstimation(double elementsCountSum, double dataSize) {
        return elementsCountSum / dataSize;
    }

    private double calculateFrequency(double dataSize, double intervalSize) {
        return intervalSize / dataSize;
    }

    private double calculateElementsCountSum(Interval interval, Interval previousInterval) {
        if (previousInterval == null) {
            return interval.getValues().length;
        }
        return interval.getIntervalSize() + previousInterval.getIntervalStats().elementCountSum();
    }

    protected double calculateAverageFailureMileage(@NonNull List<Interval> intervals) {
        return intervals
                .stream()
                .mapToDouble(i -> i.getMiddleValue() * i.getIntervalStats().frequency())
                .sum();
    }

    /**
     * Метод вычисления дисперсии
     *
     * @param intervals интервалы, по которым следует вычислять дисперсию
     * @return рассчитанная дисперсия
     */

    protected double calculateVariance(List<Interval> intervals) {
        double averageFailureMileage = calculateAverageFailureMileage(intervals);
        return intervals
                .stream()
                .mapToDouble(i -> Math.pow(i.getMiddleValue() - averageFailureMileage, 2) * i.getIntervalStats().frequency())
                .sum();
    }

    /**
     * Метод вычисления среднеквадратического отклонения
     *
     * @param variance дисперсия распределения
     * @return среднеквадратическое отклонение
     */
    protected double calculateStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }

    public double calculateVariationCoefficient(double averageFailureMileage, double standardDeviation) {
        return standardDeviation / averageFailureMileage;
    }
}
