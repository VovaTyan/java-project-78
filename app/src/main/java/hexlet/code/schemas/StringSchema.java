package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema required() {
        addPredicates("requaredStringSchema", n -> n != null && n.toString().length() > 0);
        return this;
    }
    public StringSchema contains(String contein) {
        addPredicates("containsStringSchema", n -> n.toString().contains(contein));
        return this;
    }
    public StringSchema minLength(Integer min) {
        addPredicates("minLengthStringSchema", n -> n.toString().length() >= min);
        return this;
    }
}
