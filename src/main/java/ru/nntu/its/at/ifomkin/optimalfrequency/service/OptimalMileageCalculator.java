package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.StandardFunctionUtil;

@Service
public class OptimalMileageCalculator {
    public double calculateOptimalMileageValue(double acceptableLevelOfFailureFreeOperation, double standardDeviation, double averageFailureMileage) {
        var normalFunctionValue = StandardFunctionUtil.findFunctionArgumentByFunctionValue(acceptableLevelOfFailureFreeOperation);
        return normalFunctionValue * standardDeviation + averageFailureMileage;
    }
}
