package hexlet.code.schemas;

import java.util.function.Predicate;

import static hexlet.code.schemas.MapSchema.getNameBaseSvhema;

public final class NumberSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicatePozitive;
    private static Predicate predicateRange;
    private String string = "NumberSchema";
    public NumberSchema required() {
        predicateRequared = n -> n instanceof Integer;
        addPredicates(predicateRequared);
        string = string + ".required";
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
        string = string + ".positive";
        return this;
    }
    public NumberSchema range(int min, int max) {
        predicateRange = n -> (int) n >= (int) min && (int) n <= (int) max;
        addPredicates(predicateRange);
        string = string + ".range";
        return this;
    }
    @Override
    public String toString() {
        return string;
    }
    public static void checked() {
        if (getNameBaseSvhema().contains("required")) {
            MapSchema.addShapePredicates(predicateRequared);
        }
        if (getNameBaseSvhema().contains("positive")) {
            MapSchema.addShapePredicates(predicatePozitive);
        }
        if (getNameBaseSvhema().contains("range")) {
            MapSchema.addShapePredicates(predicateRange);
        }
    }
}
