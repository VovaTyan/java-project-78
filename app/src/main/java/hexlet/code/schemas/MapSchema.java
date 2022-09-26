package hexlet.code.schemas;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    private static Predicate predicateRequared;
    private static Predicate predicateSizeof;
    private static Predicate predicateShape;

    public static void setM(Predicate predicate) {
        MapSchema.predicateShape = predicate;
    }

    public void shape(Map<String, BaseSchema> schemas) {
        addPredicates(n -> {
            for (Map.Entry<String, BaseSchema> schema: schemas.entrySet()) {
                for (Map.Entry<String, Object> data : ((Map<String, Object>) n).entrySet()) {
                    if (schema.getKey().equals(data.getKey()) && !isValid(schema.getValue(), data.getValue())) {
                        return false;
                    }
                }
            }
            return true;
        });
    }
    boolean isValid(BaseSchema schema, Object value) {
        try {
            schema.getClass().getDeclaredMethod("checked").invoke(null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!predicateShape.test(value)) {
            return false;
        }
        return true;
    }
    public void required() {
        predicateRequared = n -> n instanceof Map<?, ?>;
        addPredicates(predicateRequared);
    }
    public void sizeof(int amountChar) {
        predicateSizeof = n -> ((Map<?, ?>) n).size() == amountChar;
        addPredicates(predicateSizeof);
    }
    public static void checked() {
        if (predicateRequared != null) {
            MapSchema.setM(predicateRequared);
        }
        if (predicateSizeof != null) {
            MapSchema.setM(predicateSizeof);
        }
    }
}
