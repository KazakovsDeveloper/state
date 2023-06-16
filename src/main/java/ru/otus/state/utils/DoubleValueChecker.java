package ru.otus.state.utils;

import java.util.function.Predicate;

public interface DoubleValueChecker {

    Predicate<Double> isInfiniteOrNan = (doubleVal) -> Double.isInfinite(doubleVal) || Double.isNaN(doubleVal);

    default void valueChecker(double value) {
        if (isInfiniteOrNan.test(value) || value < 0.0) {
            throw new RuntimeException("Переданное значение некорректно " + value);
        }
    }

}
