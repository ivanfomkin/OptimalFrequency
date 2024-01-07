package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nntu.its.at.ifomkin.optimalfrequency.DoubleArrayProvider;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Interval;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.IntervalStats;

import java.util.List;
import java.util.stream.Stream;

class IntervalServiceTest implements DoubleArrayProvider {
    private final IntervalService intervalService = new IntervalService();

    public static Stream<Arguments> calculateIntervalLength_InputSomeArrayAndIntervalsCount_returnExpectedStep() {
        return Stream.of(
                Arguments.of(new double[]{50, 2100, 4500, 1800, 2500,
                                2400, 2600, 2700, 2100, 3800,
                                1300, 1600, 2600, 3500, 2600,
                                1500, 1300, 900, 5000, 3400,
                                2500, 2100, 2900, 3100, 2300,
                                3500, 2600, 2800, 2300, 2100
                        },
                        5,
                        990
                ),
                Arguments.of(new double[]{50, 2100, 4500, 1800, 2500,
                                2400, 2600, 2700, 2100, 3800,
                                1300, 1600, 2600, 3500, 2600,
                                1500, 1300, 900, 5000, 3400,
                                2500, 2100, 2900, 3100, 2300,
                                3500, 2600, 2800, 2300, 2100
                        },
                        6,
                        825
                ),
                Arguments.of(new double[]{50, 2100, 4500, 1800, 2500,
                                2400, 2600, 2700, 2100, 3800,
                                1300, 1600, 2600, 3500, 2600,
                                1500, 1300, 900, 5000, 3400,
                                2500, 2100, 2900, 3100, 2300,
                                3500, 2600, 2800, 2300, 2100
                        },
                        7,
                        707.1428571
                ),
                Arguments.of(
                        new double[]{
                                79522, 82943, 54732, 76410, 67411,
                                50252, 88876, 65570, 59270, 76258,
                                69379, 65392, 62661, 72032, 80958,
                                95198, 63970, 96810, 61696, 23468,
                                53789, 40915, 66688, 28500, 74158,
                                50091, 85129, 84023, 85539, 20211,
                                81232, 76108, 70359, 55530, 32495,
                                60601, 20748, 80609, 71353, 76353,
                                104284, 92698, 92548, 66500, 60123,
                                83142, 99858, 85427, 62300, 63904,
                                62030, 69684, 124568, 71107, 81355,
                                55217, 74037, 59117, 28669, 73769,
                                102640, 89415, 93950, 88443, 70369,
                                59469, 116127, 54875
                        },
                        6,
                        17392.833333333
                )
        );
    }

    @CsvSource({"30,5", "60,6", "100, 6", "200,8", "15,4", "10,4", "35,5", "50,5"})
    @ParameterizedTest
    void calculateIntervalsCount_inputArray_returnExpectedIntervalsCount(int arraySize, long expectedIntervalsCount) {
        //given
        double[] inputArray = generateArray(arraySize);

        //when
        long intervalsCount = intervalService.calculateIntervalsCount(inputArray);

        //then
        Assertions.assertEquals(expectedIntervalsCount, intervalsCount);
    }

    @MethodSource
    @ParameterizedTest
    void calculateIntervalLength_InputSomeArrayAndIntervalsCount_returnExpectedStep(double[] array, long intervalsCount, double expectedLength) {
        double actualIntervalLength = intervalService.calculateIntervalLength(array, intervalsCount);
        Assertions.assertEquals(expectedLength, actualIntervalLength, 0.00001);
    }

    @SuppressWarnings("java:S5961")
    @Test
    @DisplayName("""
            Тест проверяет, что выборка корректно разбита на интервалы, а так же каждый интервал имеет корректное
            начальное и конечное значение, число элементов в интервале и корректную середину интервала
            """)
    void divideDataToIntervals_inputTestArrayWith60Elements_arrayDividedToIntervalsCorrectly() {
        //given
        double[] inputData = {
                1.84, 0.16, 4.94, 3.13, 4.24, 0.67, 4.77, 3.07, 1.37, 4.78, 5.93, 2.59, 4.19, 4.76, 5.27,
                3.35, 0.77, 4.99, 5.41, 2.19, 3.71, 6.53, 1.77, 1.22, 0.60, 6.28, 7.80, 5.03, 3.19, 3.34,
                2.66, 2.47, 2.04, 2.86, 7.14, 6.59, 3.53, 5.36, 5.08, 5.51, 3.56, 6.06, 3.36, 6.06, 3.27,
                6.40, 1.44, 2.32, 4.74, 6.48, 4.97, 3.30, 2.89, 4.26, 7.90, 6.17, 2.16, 3.35, 6.55, 5.14
        };

        //when
        List<Interval> intervals = intervalService.divideDataToIntervals(inputData, 6);
        int totalElementsInIntervalCount = intervals.stream().map(i -> i.getValues().length).mapToInt(Integer::intValue).sum();

        //then
        Assertions.assertEquals(6, intervals.size());
        Assertions.assertEquals(inputData.length, totalElementsInIntervalCount);
        intervals.forEach(Assertions::assertNotNull);

        Assertions.assertEquals(0.16, intervals.get(0).getStartValue());
        Assertions.assertEquals(1.45, intervals.get(0).getEndValue());
        Assertions.assertEquals(7, intervals.get(0).getValues().length);
        Assertions.assertEquals(7, intervals.get(0).getIntervalSize());
        Assertions.assertEquals(0.805, intervals.get(0).getMiddleValue(), 0.0001);

        Assertions.assertEquals(1.45, intervals.get(1).getStartValue());
        Assertions.assertEquals(2.74, intervals.get(1).getEndValue());
        Assertions.assertEquals(9, intervals.get(1).getValues().length);
        Assertions.assertEquals(9, intervals.get(1).getIntervalSize());
        Assertions.assertEquals(2.095, intervals.get(1).getMiddleValue(), 0.0001);

        Assertions.assertEquals(2.74, intervals.get(2).getStartValue());
        Assertions.assertEquals(4.03, intervals.get(2).getEndValue());
        Assertions.assertEquals(14, intervals.get(2).getValues().length);
        Assertions.assertEquals(14, intervals.get(2).getIntervalSize());
        Assertions.assertEquals(3.385, intervals.get(2).getMiddleValue(), 0.0001);

        Assertions.assertEquals(4.03, intervals.get(3).getStartValue());
        Assertions.assertEquals(5.32, intervals.get(3).getEndValue());
        Assertions.assertEquals(14, intervals.get(3).getValues().length);
        Assertions.assertEquals(14, intervals.get(3).getIntervalSize());
        Assertions.assertEquals(4.675, intervals.get(3).getMiddleValue(), 0.0001);

        Assertions.assertEquals(5.32, intervals.get(4).getStartValue());
        Assertions.assertEquals(6.61, intervals.get(4).getEndValue());
        Assertions.assertEquals(13, intervals.get(4).getValues().length);
        Assertions.assertEquals(13, intervals.get(4).getIntervalSize());
        Assertions.assertEquals(5.965, intervals.get(4).getMiddleValue(), 0.0001);

        Assertions.assertEquals(6.61, intervals.get(5).getStartValue());
        Assertions.assertEquals(7.9, intervals.get(5).getEndValue());
        Assertions.assertEquals(3, intervals.get(5).getValues().length);
        Assertions.assertEquals(3, intervals.get(5).getIntervalSize());
        Assertions.assertEquals(7.255, intervals.get(5).getMiddleValue(), 0.0001);
    }


    @SuppressWarnings("java:S5961")
    @Test
    @DisplayName("Тест проверяет значения статистических данных интервалов")
    void divideDataToIntervals_inputTestArrayWith60Elements_intervalStatsIsCorrect() {
        //given
        double[] inputData = {
                1.84, 0.16, 4.94, 3.13, 4.24, 0.67, 4.77, 3.07, 1.37, 4.78, 5.93, 2.59, 4.19, 4.76, 5.27,
                3.35, 0.77, 4.99, 5.41, 2.19, 3.71, 6.53, 1.77, 1.22, 0.60, 6.28, 7.80, 5.03, 3.19, 3.34,
                2.66, 2.47, 2.04, 2.86, 7.14, 6.59, 3.53, 5.36, 5.08, 5.51, 3.56, 6.06, 3.36, 6.06, 3.27,
                6.40, 1.44, 2.32, 4.74, 6.48, 4.97, 3.30, 2.89, 4.26, 7.90, 6.17, 2.16, 3.35, 6.55, 5.14
        };

        //when
        List<Interval> intervals = intervalService.divideDataToIntervals(inputData, 6);
        List<IntervalStats> intervalStats = intervals.stream().map(Interval::getIntervalStats).toList();

        //then
        Assertions.assertEquals(6, intervals.size());
        Assertions.assertEquals(6, intervalStats.size());
        intervalStats.forEach(Assertions::assertNotNull);


        Assertions.assertEquals(7, intervalStats.get(0).size());
        Assertions.assertEquals(7, intervalStats.get(0).elementCountSum(), 0.001);
        Assertions.assertEquals(0.11667, intervalStats.get(0).frequency(), 0.001);
        Assertions.assertEquals(0.11667, intervalStats.get(0).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0.8833, intervalStats.get(0).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.09044, intervalStats.get(0).densityEstimation(), 0.001);

        Assertions.assertEquals(9, intervalStats.get(1).size());
        Assertions.assertEquals(16, intervalStats.get(1).elementCountSum(), 0.001);
        Assertions.assertEquals(0.15, intervalStats.get(1).frequency(), 0.001);
        Assertions.assertEquals(0.2667, intervalStats.get(1).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0.7333, intervalStats.get(1).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.20672, intervalStats.get(1).densityEstimation(), 0.001);

        Assertions.assertEquals(14, intervalStats.get(2).size());
        Assertions.assertEquals(30, intervalStats.get(2).elementCountSum(), 0.001);
        Assertions.assertEquals(0.2333, intervalStats.get(2).frequency(), 0.001);
        Assertions.assertEquals(0.5, intervalStats.get(2).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0.5, intervalStats.get(2).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.387597, intervalStats.get(2).densityEstimation(), 0.001);

        Assertions.assertEquals(14, intervalStats.get(3).size());
        Assertions.assertEquals(44, intervalStats.get(3).elementCountSum(), 0.001);
        Assertions.assertEquals(0.2333, intervalStats.get(3).frequency(), 0.001);
        Assertions.assertEquals(0.7333, intervalStats.get(3).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0.2667, intervalStats.get(3).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.5684755, intervalStats.get(3).densityEstimation(), 0.001);

        Assertions.assertEquals(13, intervalStats.get(4).size());
        Assertions.assertEquals(57, intervalStats.get(4).elementCountSum(), 0.001);
        Assertions.assertEquals(0.21667, intervalStats.get(4).frequency(), 0.001);
        Assertions.assertEquals(0.95, intervalStats.get(4).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0.05, intervalStats.get(4).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.736434, intervalStats.get(4).densityEstimation(), 0.001);

        Assertions.assertEquals(3, intervalStats.get(5).size());
        Assertions.assertEquals(60, intervalStats.get(5).elementCountSum(), 0.001);
        Assertions.assertEquals(0.05, intervalStats.get(5).frequency(), 0.001);
        Assertions.assertEquals(1, intervalStats.get(5).probabilityEstimation(), 0.001);
        Assertions.assertEquals(0, intervalStats.get(5).probabilityOfFailureFreeOperation(), 0.001);
        Assertions.assertEquals(0.775194, intervalStats.get(5).densityEstimation(), 0.001);
    }

    @Test
    void calculateAverageFailureTime_calculateForOneInterval_returnExpectedValue() {
        //given
        Interval interval = new Interval(new double[]{}, 1, 0, 20, 10);
        interval.setIntervalStats(new IntervalStats(1, 1, 5, 1, 1, 1));
        List<Interval> intervals = List.of(interval);

        //when
        double averageFailureMileage = intervalService.calculateAverageFailureMileage(intervals);

        //then
        Assertions.assertEquals(50, averageFailureMileage);
    }

    @Test
    void calculateAverageFailureTime_calculateFor3Intervals_returnExpectedValue() {
        //given
        Interval interval1 = new Interval(new double[]{}, 1, 0, 20, 10);
        interval1.setIntervalStats(new IntervalStats(1, 1, 0.5, 1, 1, 1));
        Interval interval2 = new Interval(new double[]{}, 1, 0, 20, 100);
        interval2.setIntervalStats(new IntervalStats(1, 1, 0.4, 1, 1, 1));
        Interval interval3 = new Interval(new double[]{}, 1, 0, 20, 30);
        interval3.setIntervalStats(new IntervalStats(1, 1, 0.1, 1, 1, 1));
        List<Interval> intervals = List.of(interval1, interval2, interval3);

        //when
        double averageFailureMileage = intervalService.calculateAverageFailureMileage(intervals);

        //then
        Assertions.assertEquals(48, averageFailureMileage);
    }
}