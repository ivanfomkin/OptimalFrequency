package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OptimalMileageCalculatorTest {

    private final OptimalMileageCalculator optimalMileageCalculator = new OptimalMileageCalculator();

    @Test
    void calculateOptimalMileageValue_calculateMileageWithCorrectData_returnCorrectValue() {
        //given
        double acceptableLevelOfFailureFreeOperation = 0.05;
        double standardDeviation = 933;
        double averageFailureMileage = 2502.5;

        //when
        double optimalValue = optimalMileageCalculator.calculateOptimalMileageValue(acceptableLevelOfFailureFreeOperation, standardDeviation, averageFailureMileage);

        //then
        Assertions.assertEquals(963., optimalValue, 0.1);
    }
}