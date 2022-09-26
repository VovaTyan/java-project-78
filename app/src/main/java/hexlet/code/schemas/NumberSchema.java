package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicatePozitive;
    private static Predicate predicateRange;

    public NumberSchema required() {
        predicateRequared = n -> n instanceof Integer;
        addPredicates(predicateRequared);
        return this;
    }
    public NumberSchema positive() {
        predicatePozitive = n -> {
            if (n == null) {
                return true;
            } else {
                if (n instanceof Integer) {
                    return (int) n > 0;
                } else {
                    return false;
                }
            }
        };
        addPredicates(predicatePozitive);
        return this;
    }
    public NumberSchema range(int min, int max) {
        predicateRange = n -> (int) n >= (int) min && (int) n <= (int) max;
        addPredicates(predicateRange);
        return this;
    }
    public static void checked() {
        if (predicateRequared != null) {
            MapSchema.setM(predicateRequared);
        }
        if (predicatePozitive != null) {
            MapSchema.setM(predicatePozitive);
        }
        if (predicateRange != null) {
            MapSchema.setM(predicateRange);
        }
    }
}
