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
    public void testStringValidator() {
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
    public void testNumberValidator() {
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
    public void testMapValidator() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap())).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap())).isTrue();

        schema.sizeof(NUMBERS_2);
        assertThat(schema.isValid(new HashMap())).isFalse();
        Map<String, String> actual1 = new HashMap<>();
        actual1.put("key1", "value1");
        assertThat(schema.isValid(actual1)).isFalse();
        actual1.put("key2", "value2");
        assertThat(schema.isValid(actual1)).isTrue();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required().contains("ya"));
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> actual2 = new HashMap<>();
        actual2.put("name", "Kolya");
        actual2.put("age", NUMBERS_100);
        assertThat(schema.isValid(actual2)).isTrue();

        Map<String, Object> actual3 = new HashMap<>();
        actual3.put("name", "Maya");
        actual3.put("age", null);
        assertThat(schema.isValid(actual3)).isTrue();

        Map<String, Object> actual4 = new HashMap<>();
        actual4.put("name", "");
        actual4.put("age", null);
        assertThat(schema.isValid(actual4)).isFalse();

        Map<String, Object> actual5 = new HashMap<>();
        actual5.put("name", "Valya");
        actual5.put("age", -NUMBERS_5);
        assertThat(schema.isValid(actual5)).isFalse();

        Map<String, Object> actual6 = new HashMap<>();
        actual6.put("name", "Ada");
        actual6.put("age", NUMBERS_10);
        assertThat(schema.isValid(actual6)).isFalse();
    }
}
