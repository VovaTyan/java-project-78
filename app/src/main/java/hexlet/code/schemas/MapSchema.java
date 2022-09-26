package hexlet.code.schemas;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicateSizeof;
    private static List<Predicate> shapePredicates = new ArrayList<>();
    private static String nameBaseSvhema;

    public static String getNameBaseSvhema() {
        return nameBaseSvhema;
    }

    public static void addShapePredicates(Predicate predicate) {
        MapSchema.shapePredicates.add(predicate);
    }

    public void shape(Map<String, BaseSchema> schemas) {
        addPredicates(n -> {
            for (Map.Entry<String, BaseSchema> schema: schemas.entrySet()) {
                for (Map.Entry<String, Object> data : ((Map<String, Object>) n).entrySet()) {
                    if (schema.getKey().equals(data.getKey())) {
                        if (!isValid(schema.getValue(), data.getValue())) {
                            return false;
                        }
                    }
                }
            }
            return true;
        });
    }
    boolean isValid(BaseSchema schema, Object value) {
        shapePredicates.clear();
        nameBaseSvhema = schema.toString();
        try {
            schema.getClass().getDeclaredMethod("checked").invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        boolean result = true;
        for (Predicate predicate : shapePredicates) {
            result = result && predicate.test(value);
        }
        return result;
    }
    public void required() {
        predicateRequared = n -> n instanceof Map<?, ?>;
        addPredicates(predicateRequared);
    }
    public void sizeof(int amountChar) {
        predicateSizeof = n -> ((Map<?, ?>) n).size() == amountChar;
        addPredicates(predicateSizeof);
    }
}
