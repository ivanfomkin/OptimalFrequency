package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.util.Precision;
import ru.nntu.its.at.ifomkin.optimalfrequency.exception.FindFunctionArgumentException;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class StandardFunctionUtil {
    private final UnivariateFunction NORMALIZED_NORMAL_DISTRIBUTION_FUNCTION_INTEGRAL = x -> Math.exp(-Math.pow(x, 2) / 2);

    // Метод для численного интегрирования от x до минус бесконечности
    public static double calculateNormalizedNormalDistributionFunction(double x) {
        UnivariateIntegrator integrator = new TrapezoidIntegrator();
        return Precision.round(integrator.integrate(30_000_000, NORMALIZED_NORMAL_DISTRIBUTION_FUNCTION_INTEGRAL, -500, x) / Math.sqrt(2 * Math.PI), 4);
    }


    public static double findFunctionArgumentByFunctionValue(double functionValue) {
        return findFunctionArgumentByValueAndEps(functionValue, 0.001);
    }

    private static double findFunctionArgumentByValueAndEps(double functionValue, double eps) {
        if (FunctionArgumentsCache.containsValue(functionValue)) {
            return FunctionArgumentsCache.getValue(functionValue);
        }
        List<Double> acceptableValues = getAcceptableFunctionArguments();
        for (Double value : acceptableValues) {
            var currentFunctionValue = calculateNormalizedNormalDistributionFunction(value);
            if (Precision.equals(currentFunctionValue, functionValue, eps)) {
                FunctionArgumentsCache.updateCache(functionValue, value);
                return value;
            }
        }
        if (eps <= 0.1) {
            return findFunctionArgumentByValueAndEps(functionValue, eps * 10);
        }
        throw new FindFunctionArgumentException("Не найдено значение аргумента для функции со значением " + functionValue);
    }

    private static List<Double> getAcceptableFunctionArguments() {
        List<Double> acceptableFunctionArguments = new ArrayList<>();
        double minValue = -2.1;
        double maxValue = -1;
        double step = 0.05;
        double cursor = minValue;
        while (cursor <= maxValue) {
            acceptableFunctionArguments.add(cursor);
            cursor = cursor + step;
        }
        return acceptableFunctionArguments;
    }
}
