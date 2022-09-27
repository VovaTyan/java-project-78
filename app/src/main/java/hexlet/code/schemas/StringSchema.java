package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema required() {
        addPredicates(n -> n != null && n.toString().length() > 0);
        return this;
    }
    public StringSchema contains(String contein) {
        addPredicates(n -> n.toString().contains(contein));
        return this;
    }
    public StringSchema minLength(Integer min) {
        addPredicates(n -> n.toString().length() >= min);
        return this;
    }
}
