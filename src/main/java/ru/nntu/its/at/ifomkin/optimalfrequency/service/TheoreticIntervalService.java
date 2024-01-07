package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Interval;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.TheoreticInterval;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.StandardFunctionUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheoreticIntervalService {
    public List<TheoreticInterval> createTheoreticIntervals(List<Interval> actualIntervals, double averageFailureMileage, double standardDeviation) {
        var totalElements = actualIntervals.stream().mapToInt(e -> e.getValues().length).sum();
        List<TheoreticInterval> theoreticIntervals = new ArrayList<>(actualIntervals.size());
        for (Interval actualInterval : actualIntervals) {
            double middleValue = actualInterval.getMiddleValue();
            double functionValue = calculateFunctionValue(averageFailureMileage, standardDeviation, middleValue);
            double intervalLength = actualInterval.getEndValue() - actualInterval.getStartValue();
            double eventsNumber = functionValue * intervalLength * totalElements;
            var frequency = functionValue * intervalLength;
            var difference = Math.pow(actualInterval.getValues().length - eventsNumber, 2) / eventsNumber;
            var probabilityEstimation = calculateProbabilityEstimation(middleValue, averageFailureMileage, standardDeviation);
            var theoreticInterval = new TheoreticInterval(
                    middleValue,
                    functionValue,
                    eventsNumber,
                    frequency,
                    probabilityEstimation,
                    1 - probabilityEstimation,
                    difference
            );
            theoreticIntervals.add(theoreticInterval);
        }
        return theoreticIntervals;
    }

    protected double calculateFunctionValue(double averageFailureMileage, double standardDeviation, double argumentValue) {
        return (1 / (standardDeviation * Math.sqrt(2 * Math.PI))) * Math.exp(-Math.pow(argumentValue - averageFailureMileage, 2) / (2 * Math.pow(standardDeviation, 2)));
    }

    protected double calculateProbabilityEstimation(double middleValue, double averageValue, double standardDeviation) {
        double functionArgument = (middleValue - averageValue) / standardDeviation;
        return StandardFunctionUtil.calculateNormalizedNormalDistributionFunction(functionArgument);
    }
}
