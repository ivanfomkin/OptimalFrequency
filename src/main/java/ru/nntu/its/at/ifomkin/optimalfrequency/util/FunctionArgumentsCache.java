package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class FunctionArgumentsCache {
    private static final Map<Double, Double> CACHE = new ConcurrentHashMap<>();

    public void updateCache(double functionValue, double functionArgument) {
        CACHE.put(functionValue, functionArgument);
    }

    public Double getValue(double functionValue) {
        return CACHE.get(functionValue);
    }

    public boolean containsValue(double functionValue) {
        return CACHE.containsValue(functionValue);
    }
}
