package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Dataset;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Interval;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.TheoreticInterval;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetService {
    private final ChartService chartService;
    private final IntervalService intervalService;
    private final PirsonCriteriaService pirsonCriteriaService;
    private final TheoreticIntervalService theoreticIntervalService;
    private final OptimalMileageCalculator optimalMileageCalculator;

    public Dataset calculateOptimalMileage(double[] data, double acceptableLevelOfFailureFreeOperation, double significanceLevel) {
        List<Interval> actualIntervals = getIntervals(data);
        double variance = intervalService.calculateVariance(actualIntervals);
        double averageFailureMileage = intervalService.calculateAverageFailureMileage(actualIntervals);
        double standardDeviation = intervalService.calculateStandardDeviation(variance);
        double variationCoefficient = intervalService.calculateVariationCoefficient(averageFailureMileage, standardDeviation);
        List<TheoreticInterval> theoreticIntervals = theoreticIntervalService.createTheoreticIntervals(actualIntervals, averageFailureMileage, standardDeviation);
        double optimalMileage = optimalMileageCalculator.calculateOptimalMileageValue(acceptableLevelOfFailureFreeOperation, standardDeviation, averageFailureMileage);
        Dataset.NormalDistributionCriteria distributionCriteria = pirsonCriteriaService.calculateNormalDistributionCriteria(theoreticIntervals, significanceLevel);
        String histogram = chartService.createDistributionDensityHistogram(data, actualIntervals.size());
        String functionGraph = chartService.createHistogramBase64Image(data, averageFailureMileage, standardDeviation, optimalMileage);
        return Dataset.builder()
                .actualIntervals(actualIntervals)
                .theoreticIntervals(theoreticIntervals)
                .variance(variance)
                .averageFailureMileage(averageFailureMileage)
                .standardDeviation(standardDeviation)
                .optimalMileage(optimalMileage)
                .histogram(histogram)
                .functionGraph(functionGraph)
                .normalDistributionCriteria(distributionCriteria)
                .variationCoefficient(variationCoefficient)
                .build();
    }

    private List<Interval> getIntervals(double[] data) {
        int intervalCount = intervalService.calculateIntervalsCount(data);
        return intervalService.divideDataToIntervals(data, intervalCount);
    }
}
