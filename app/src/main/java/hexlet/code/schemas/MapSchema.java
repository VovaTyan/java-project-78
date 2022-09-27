package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {
    public void shape(Map<String, BaseSchema> schemas) {
        addPredicates("shapeMapSchema", n -> {
            for (Map.Entry<String, BaseSchema> schema : schemas.entrySet()) {
                for (Map.Entry<String, Object> data : ((Map<String, Object>) n).entrySet()) {
                    if (schema.getKey().equals(data.getKey()) && (!schema.getValue().isValid(data.getValue()))) {
                        return false;
                    }
                }
            }
            return true;
        });
    }
    public void required() {
        addPredicates("requiredMapSchema", n -> n instanceof Map<?, ?>);
    }

    public void sizeof(int amountChar) {
        addPredicates("sizeofMapSchema", n -> ((Map<?, ?>) n).size() == amountChar);
    }
}
