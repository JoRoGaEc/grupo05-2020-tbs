package com.sif.digestyc.utilities.rendimiento;
import java.time.Duration;
import java.time.Instant;

public class MeasurementUtils {

    public static void MeasureElapsedTime(String description, Action0 action) {
        Duration duration = MeasureElapsedTime(action);

        System.out.println(String.format("[%s] %s", description, duration));
    }

    private static Duration MeasureElapsedTime(Action0 action) {
        Instant start = Instant.now();

        action.invoke();

        Instant end = Instant.now();

        return Duration.between(start, end);
    }
}