package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StandardFunctionUtilTest {

    @CsvSource({
            "0.0,0.500", "0.1,0.540", "0.2,0.579", "0.3,0.618", "0.4,0.655", "0.5,0.691", "0.6,0.726", "0.7,0.758",
            "0.8,0.788", "0.9,0.816", "1.0,0.841", "1.1,0.864", "1.2,0.885", "1.3,0.903", "1.4,0.919", "1.5,0.933",
            "1.6,0.945", "1.7,0.955", "1.8,0.964", "1.9,0.971", "2.0,0.977", "2.1,0.982", "2.2,0.986", "2.3,0.989",
            "2.4,0.992", "2.5,0.994", "2.6,0.995", "2.7,0.996", "2.8,0.997", "2.9,0.998", "-0.1,0.460", "-0.2,0.421",
            "-0.3,0.382", "-0.4,0.345", "-0.5,0.309", "-0.6,0.274", "-0.7,0.242", "-0.8,0.212", "-0.9,0.184", "-1.0,0.159",
            "-1.1,0.136", "-1.2,0.115", "-1.3,0.097", "-1.4,0.081", "-1.5,0.067", "-1.6,0.055", "-1.7,0.045", "-1.8,0.036",
            "-1.9,0.029", "-2.0,0.023", "-2.1,0.018", "-2.2,0.014", "-2.3,0.011", "-2.4,0.008", "-2.5,0.006", "-2.6,0.005",
            "-2.7,0.004", "-2.8,0.003", "-2.9,0.002"

    })
    @ParameterizedTest
    void integrateFromMinusInfinity_integrateWithPrecision0_001_resultEqualsExpectedValue(double z, double expectedValue) {
        double functionValueInZPoint = StandardFunctionUtil.calculateNormalizedNormalDistributionFunction(z);
        System.out.println("z = " + z + " intgrl = " + functionValueInZPoint);
        Assertions.assertEquals(expectedValue, functionValueInZPoint, 0.001);
    }

    @CsvSource({
            "-3.0,0.0013", "-3.1,0.001", "-3.2,0.0007", "-3.3,0.0005", "-3.4,0.0003", "-3.5,0.0002", "-3.6,0.0001", "-3.7,0.0001",
            "-3.8,0.0001", "-3.9,0.0000", "3.0,0.9987", "3.1,0.9990", "3.2,0.9993", "3.3,0.9995", "3.4,0.9997", "3.5,0.9998",
            "3.6,0.9998", "3.7,0.9999", "3.8,0.9999", "3.9,1.0000"
    })
    @ParameterizedTest
    void integrateFromMinusInfinity_integrateWithPrecision0_0001_resultEqualsExpectedValue(double z, double expectedValue) {
        double functionValueInZPoint = StandardFunctionUtil.calculateNormalizedNormalDistributionFunction(z);
        System.out.println("z = " + z + " intgrl = " + functionValueInZPoint);
        Assertions.assertEquals(expectedValue, functionValueInZPoint, 0.0001);
    }
}