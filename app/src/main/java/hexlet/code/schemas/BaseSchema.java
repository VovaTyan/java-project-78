package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema {
    private Map<String, Predicate> predicates = new HashMap<>();
    public final void addPredicates(String nameSchema, Predicate predicate) {
        predicates.put(nameSchema, predicate);
    }
    public final boolean isValid(Object data) {
        for (Map.Entry<String, Predicate> predicate: predicates.entrySet()) {
            if (predicate.getKey().contains(this.getClass().getSimpleName()) && !predicate.getValue().test(data)) {
                return false;
            }
        }
        return true;
    }
}
