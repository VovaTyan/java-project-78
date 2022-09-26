package hexlet.code.schemas;

import java.util.function.Predicate;

import static hexlet.code.schemas.MapSchema.getNameBaseSvhema;

public final class StringSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicateContains;
    private static Predicate predicateminLength;
    private String string = "StringSchema";
    public StringSchema required() {
        predicateRequared = n -> n != null && n.toString().length() > 0;
        addPredicates(predicateRequared);
        string = string + ".required";
        return this;
    }
    public StringSchema contains(String contein) {
        predicateContains = n -> n.toString().contains(contein);
        addPredicates(predicateContains);
        string = string + ".contains";
        return this;
    }
    public StringSchema minLength(int min) {
        predicateminLength = n -> n.toString().length() >= min;
        addPredicates(predicateminLength);
        string = string + ".minLength";
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
        if (getNameBaseSvhema().contains("contains")) {
            MapSchema.addShapePredicates(predicateContains);
        }
        if (getNameBaseSvhema().contains("minLength")) {
            MapSchema.addShapePredicates(predicateminLength);
        }
    }
}
