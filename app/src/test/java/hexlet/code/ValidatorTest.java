package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import hexlet.code.schemas.NumberSchema;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidatorTest {
    private static final Integer NUMBERS_1 = 1;
    private static final Integer NUMBERS_2 = 2;
    private static final Integer NUMBERS_5 = 5;
    private static final Integer NUMBERS_10 = 10;
    private static final Integer NUMBERS_21 = 21;
    private static final Integer NUMBERS_22 = 22;
    private static final Integer NUMBERS_100 = 100;

    @Test
    public void testStringSchema() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();
        schema.required();

        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();

        assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("at").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();

        assertThat(schema.isValid("what does the fox say")).isFalse();

        schema = v.string();
        assertThat(schema.minLength(NUMBERS_21).isValid("what does the fox say")).isTrue();
        assertThat(schema.minLength(NUMBERS_22).isValid("what does the fox say")).isFalse();

        assertThat(schema.isValid("what does the fox say")).isFalse();
    }

    @Test
    public void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();

        schema.positive();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(NUMBERS_100)).isTrue();
        assertThat(schema.isValid("5")).isFalse();

        assertThat(schema.positive().isValid(NUMBERS_100)).isTrue();
        assertThat(schema.isValid(-NUMBERS_100)).isFalse();

        schema.range(NUMBERS_5, NUMBERS_10);

        assertThat(schema.isValid(NUMBERS_5)).isTrue();
        assertThat(schema.isValid(NUMBERS_10)).isTrue();
        assertThat(schema.isValid(NUMBERS_1)).isFalse();
        assertThat(schema.isValid(NUMBERS_100)).isFalse();

        schema.range(NUMBERS_5, NUMBERS_10);

        assertThat(schema.isValid(NUMBERS_5)).isTrue();
        assertThat(schema.isValid(NUMBERS_10)).isTrue();
        assertThat(schema.isValid(NUMBERS_1)).isFalse();
        assertThat(schema.isValid(NUMBERS_100)).isFalse();
        assertThat(schema.isValid(-NUMBERS_5)).isFalse();
    }
    @Test
    public void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap())).isTrue();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();

        schema.sizeof(NUMBERS_2);

        assertThat(schema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();
    }
    @Test
    public void testMapInnerSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", NUMBERS_2);
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema.isValid(human2)).isTrue();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(schema.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -NUMBERS_2);
        assertThat(schema.isValid(human4)).isFalse();
    }
}
