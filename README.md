### Hexlet tests and linter status:
[![Actions Status](https://github.com/VovaTyan/java-project-78/workflows/hexlet-check/badge.svg)](https://github.com/VovaTyan/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/75d0898d7a977e745c96/maintainability)](https://codeclimate.com/github/VovaTyan/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/75d0898d7a977e745c96/test_coverage)](https://codeclimate.com/github/VovaTyan/java-project-78/test_coverage)
## *Валидатор данных в полях объекта по устанавливаемым фильтрам* ##
### Реализует возможность проверять по валидаторам: ###
#### строки ####

- [X] required – любая непустая строка
- [X] minLength – строка равна или длиннее указанного числа
- [X] contains – строка содержит определённую подстроку

```
    import hexlet.code.Validator;
    import hexlet.code.schemas.StringSchema;
    import hexlet.code.schemas.BaseSchema;

    Validator v = new Validator();

    StringSchema schema = v.string();

    schema.isValid(""); // true
    schema.isValid(null); // true

    schema.required();

    schema.isValid("what does the fox say"); // true
    schema.isValid("hexlet"); // true
    schema.isValid(null); // false
    schema.isValid(""); // false

    schema.contains("wh").isValid("what does the fox say"); // true
    schema.contains("what").isValid("what does the fox say"); // true
    schema.contains("whatthe").isValid("what does the fox say"); // false

    schema.isValid("what does the fox say"); // false
    // уже false, так как добавлена ещё одна проверка contains("whatthe")
```

#### целые числа ####

- [X] required – любое число включая ноль
- [X] positive – положительное число
- [X] range – диапазон, в который должны попадать числа включая границы

```
    import hexlet.code.Validator;
    import hexlet.code.schemas.NumberSchema;
    import hexlet.code.schemas.BaseSchema;

    Validator v = new Validator();

    NumberSchema schema = v.number();

    schema.isValid(null); // true

    schema.required();

    schema.isValid(null); // false
    schema.isValid(10) // true
    schema.isValid("5"); // false

    schema.positive().isValid(10); // true
    schema.isValid(-10); // false

    schema.range(5, 10);

    schema.isValid(5); // true
    schema.isValid(10); // true
    schema.isValid(4); // false
    schema.isValid(11); // false
```

#### словари (Мар) ####

- [X] required – требуется тип данных Map
- [X] sizeof – количество пар ключ-значений в объекте Map должно быть равно заданному

```
    import hexlet.code.Validator;
    import hexlet.code.schemas.MapSchema;
    import hexlet.code.schemas.BaseSchema;

    Validator v = new Validator();

    MapSchema schema = v.map();

    schema.isValid(null); // true

    schema.required();

    schema.isValid(null) // false
    schema.isValid(new HashMap()); // true
    Map<String, String> data = new HashMap<>();
    data.put("key1", "value1");
    schema.isValid(data); // true

    schema.sizeof(2);

    schema.isValid(data);  // false
    data.put("key2", "value2");
    schema.isValid(data); // true
```

#### объект Map, c данными внутри него ####

- [X] shape - позволяет описывать валидацию для значений объекта Map по ключам

```
    import hexlet.code.Validator;
    import hexlet.code.schemas.MapSchema;
    import hexlet.code.schemas.BaseSchema;

    MapSchema schema = v.map();

    Map<String, BaseSchema> schemas = new HashMap<>();
    schemas.put("name", v.string().required());
    schemas.put("age", v.number().positive());
    schema.shape(schemas);

    Map<String, Object> human2 = new HashMap<>();
    human2.put("name", "Maya");
    human2.put("age", null);
    schema.isValid(human2); // true

    Map<String, Object> human3 = new HashMap<>();
    human3.put("name", "");
    human3.put("age", null);
    schema.isValid(human3); // false
```
