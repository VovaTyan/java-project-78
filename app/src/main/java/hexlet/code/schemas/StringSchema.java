package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicateContains;
    private static Predicate predicateminLength;
    public StringSchema required() {
        predicateRequared = n -> n != null && n.toString().length() > 0;
        addPredicates(predicateRequared);
        return this;
    }
    public StringSchema contains(String contein) {
        predicateContains = n -> n.toString().contains(contein);
        addPredicates(predicateContains);
        return this;
    }
    public StringSchema minLength(int min) {
        predicateminLength = n -> n.toString().length() >= min;
        addPredicates(predicateminLength);
        return this;
    }
    public static void checked() {
        if (predicateRequared != null) {
            MapSchema.setM(predicateRequared);
        }
        if (predicateContains != null) {
            MapSchema.setM(predicateContains);
        }
        if (predicateminLength != null) {
            MapSchema.setM(predicateminLength);
        }
    }
}
