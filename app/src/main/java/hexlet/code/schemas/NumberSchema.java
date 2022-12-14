package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {
    public NumberSchema required() {
        addPredicates(n -> n instanceof Integer);
        return this;
    }
    public NumberSchema positive() {
        addPredicates(n -> {
            if (n == null) {
                return true;
            } else {
                if (n instanceof Integer) {
                    return (int) n > 0;
                } else {
                    return false;
                }
            }
        });
        return this;
    }
    public NumberSchema range(Integer min, Integer max) {
        addPredicates(n -> (Integer) n >= min && (Integer) n <= max);
        return this;
    }
}
