package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Dataset;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.TheoreticInterval;

import java.util.List;

@Service
public class PirsonCriteriaService {

    public Dataset.NormalDistributionCriteria calculateNormalDistributionCriteria(List<TheoreticInterval> intervals, double significanceLevel) {
        double actualPirsonValue = calculateActualPirsonValue(intervals);
        int numberOfDegreesFreedom = calculateNumberOfDegreesFreedom(intervals);
        double criticalValue = getTheoreticPirsonValue(numberOfDegreesFreedom, significanceLevel);
        boolean isNormalDistribution = actualPirsonValue < criticalValue;
        return Dataset.NormalDistributionCriteria.builder()
                .pirsonValue(actualPirsonValue)
                .numberOfDegreesFreedom(numberOfDegreesFreedom)
                .normalDistribution(isNormalDistribution)
                .pirsonTheoreticValue(criticalValue)
                .build();
    }

    private double getTheoreticPirsonValue(int numberOfDegreesFreedom, double significanceLevel) {
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(numberOfDegreesFreedom);
        return chiSquaredDistribution.inverseCumulativeProbability(1 - significanceLevel);
    }

    private double calculateActualPirsonValue(List<TheoreticInterval> intervals) {
        return intervals.stream().mapToDouble(TheoreticInterval::difference).sum();
    }

    private int calculateNumberOfDegreesFreedom(List<TheoreticInterval> intervals) {
        return intervals.size() - (2 + 1);
    }
}
