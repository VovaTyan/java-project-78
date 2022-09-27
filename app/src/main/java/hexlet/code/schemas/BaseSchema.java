package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema {
    private List<Predicate> predicates = new ArrayList<>();
    public final void addPredicates(Predicate predicate) {
        predicates.add(predicate);
    }
    public final boolean isValid(Object data) {
        for (Predicate predicate: predicates) {
            if (!predicate.test(data)) {
                return false;
            }
        }
        return true;
    }
}
