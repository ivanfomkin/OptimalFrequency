package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AcceptableLevelsOfFailureFreeOperationUtil {
    public static final List<Double> ACCEPTABLE_VALUES = List.of(
            0.02,
            0.03,
            0.04,
            0.05,
            0.06,
            0.07,
            0.08,
            0.09,
            0.10,
            0.11,
            0.12,
            0.13,
            0.14,
            0.15
    );
}