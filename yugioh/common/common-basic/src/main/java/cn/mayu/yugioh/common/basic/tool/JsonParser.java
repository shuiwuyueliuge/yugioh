package cn.mayu.yugioh.common.basic.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private ObjectMapper objectMapper;

    private String json;

    private JsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static JsonParser defaultInstance() {
        return new JsonParser(new ObjectMapper());
    }

    public static JsonParserBuilder builder() {
        return new JsonParserBuilder(new ObjectMapper());
    }

    public List<Map<String, String>> parseJsonArray2Map() {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            List<Map<String, String>> objectList = Lists.newArrayList();
            Iterator<JsonNode> nodeIterator = jsonNode.elements();
            while (nodeIterator.hasNext()) {
                Map<String, String> objectMap = Maps.newHashMap();
                JsonNode node = nodeIterator.next();
                Iterator<String> nameIterator = node.fieldNames();
                while (nameIterator.hasNext()) {
                    String name = nameIterator.next();
                    objectMap.put(name, node.findValue(name).asText());
                }

                objectList.add(objectMap);
            }

            return objectList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonParser findArrayByKey(String content, String key) {
        try {
            this.json = objectMapper.readTree(content).findPath(key).toString();
            return this;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> readListValue(String content, Class<T> elementClasses)  {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, elementClasses);
        try {
            return objectMapper.readValue(content, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readObjectValue(String content, Class<T> valueType)  {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return this.json;
    }

    public static class JsonParserBuilder {

        private ObjectMapper objectMapper;

        public JsonParserBuilder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        /**
         * Feature that determines whether encountering of unknown properties (ones that do not map to a property,
         * and there is no "any setter" or handler that can handle it) should result in a failure
         * (by throwing a JsonMappingException) or not.
         * <hr/>
         * 设置false可以忽略json字符串上存在但是目标对象上不存在的属性.
         */
        public JsonParserBuilder failOnUnknownProperties(boolean state) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, state);
            return this;
        }

        public JsonParser build() {
            return new JsonParser(this.objectMapper);
        }
    }
}

/**
 * ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT
 * Feature that can be enabled to allow empty JSON Array value (that is, [ ]) to be bound to POJOs as null.
 * ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
 * Feature that can be enabled to allow JSON empty String value ("") to be bound to POJOs as null.
 * ACCEPT_FLOAT_AS_INT
 * Feature that determines whether coercion from JSON floating point number (anything with command (`.`) or exponent portion (`e` / `E')) to an expected integral number (`int`, `long`, `java.lang.Integer`, `java.lang.Long`, `java.math.BigDecimal`) is allowed or not.
 * ACCEPT_SINGLE_VALUE_AS_ARRAY
 * Feature that determines whether it is acceptable to coerce non-array (in JSON) values to work with Java collection (arrays, java.util.Collection) types.
 * ADJUST_DATES_TO_CONTEXT_TIME_ZONE
 * Feature that specifies whether context provided TimeZone (DeserializationContext.getTimeZone() should be used to adjust Date/Time values on deserialization, even if value itself contains timezone information.
 * EAGER_DESERIALIZER_FETCH
 * Feature that determines whether ObjectReader should try to eagerly fetch necessary JsonDeserializer when possible.
 * FAIL_ON_IGNORED_PROPERTIES
 * Feature that determines what happens when a property that has been explicitly marked as ignorable is encountered in input: if feature is enabled, JsonMappingException is thrown; if false, property is quietly skipped.
 * FAIL_ON_INVALID_SUBTYPE
 * Feature that determines what happens when type of a polymorphic value (indicated for example by JsonTypeInfo) can not be found (missing) or resolved (invalid class name, unmappable id); if enabled, an exception ir thrown; if false, null value is used instead.
 * FAIL_ON_MISSING_CREATOR_PROPERTIES
 * Feature that determines what happens if one or more Creator properties (properties bound to parameters of Creator method (constructor or static factory method)) are missing value to bind to from content.
 * FAIL_ON_NULL_FOR_PRIMITIVES
 * Feature that determines whether encountering of JSON null is an error when deserializing into Java primitive types (like 'int' or 'double').
 * FAIL_ON_NUMBERS_FOR_ENUMS
 * Feature that determines whether JSON integer numbers are valid values to be used for deserializing Java enum values.
 * FAIL_ON_READING_DUP_TREE_KEY
 * Feature that determines what happens when reading JSON content into tree (TreeNode) and a duplicate key is encountered (property name that was already seen for the JSON Object).
 * FAIL_ON_UNKNOWN_PROPERTIES
 * Feature that determines whether encountering of unknown properties (ones that do not map to a property, and there is no "any setter" or handler that can handle it) should result in a failure (by throwing a JsonMappingException) or not.
 * FAIL_ON_UNRESOLVED_OBJECT_IDS
 * Feature that determines what happens if an Object Id reference is encountered that does not refer to an actual Object with that id ("unresolved Object Id"): either an exception is thrown (true), or a null object is used instead (false).
 * READ_DATE_TIMESTAMPS_AS_NANOSECONDS
 * Feature that controls whether numeric timestamp values are expected to be written using nanosecond timestamps (enabled) or not (disabled), if and only if datatype supports such resolution.
 * READ_ENUMS_USING_TO_STRING
 * Feature that determines standard deserialization mechanism used for Enum values: if enabled, Enums are assumed to have been serialized using return value of Enum.toString(); if disabled, return value of Enum.name() is assumed to have been used.
 * READ_UNKNOWN_ENUM_VALUES_AS_NULL
 * Feature that allows unknown Enum values to be parsed as null values.
 * UNWRAP_ROOT_VALUE
 * Feature to allow "unwrapping" root-level JSON value, to match setting of SerializationFeature.WRAP_ROOT_VALUE used for serialization.
 * UNWRAP_SINGLE_VALUE_ARRAYS
 * Feature that determines whether it is acceptable to coerce single value array (in JSON) values to the corresponding value type.
 * USE_BIG_DECIMAL_FOR_FLOATS
 * Feature that determines whether JSON floating point numbers are to be deserialized into BigDecimals if only generic type description (either Object or Number, or within untyped Map or Collection context) is available.
 * USE_BIG_INTEGER_FOR_INTS
 * Feature that determines whether JSON integral (non-floating-point) numbers are to be deserialized into BigIntegers if only generic type description (either Object or Number, or within untyped Map or Collection context) is available.
 * USE_JAVA_ARRAY_FOR_JSON_ARRAY
 * Feature that determines whether JSON Array is mapped to Object[] or List<Object> when binding "untyped" objects (ones with nominal type of java.lang.Object).
 * USE_LONG_FOR_INTS
 * Feature that determines how "small" JSON integral (non-floating-point) numbers -- ones that fit in 32-bit signed integer (`int`) -- are bound when target type is loosely typed as Object or Number (or within untyped Map or Collection context).
 * WRAP_EXCEPTIONS
 * Feature that determines whether Jackson code should catch and wrap Exceptions (but never Errors!) to add additional information about location (within input) of problem or not.
 */


/**
 * Enum JsonGenerator.Feature
 *
 * AUTO_CLOSE_JSON_CONTENT
 * Feature that determines what happens when the generator is closed while there are still unmatched JsonToken.START_ARRAY or JsonToken.START_OBJECT entries in output content.
 * AUTO_CLOSE_TARGET
 * Feature that determines whether generator will automatically close underlying output target that is NOT owned by the generator.
 * ESCAPE_NON_ASCII
 * Feature that specifies that all characters beyond 7-bit ASCII range (i.e.
 * FLUSH_PASSED_TO_STREAM
 * Feature that specifies that calls to JsonGenerator.flush() will cause matching flush() to underlying OutputStream or Writer; if disabled this will not be done.
 * IGNORE_UNKNOWN
 * Feature that determines what to do if the underlying data format requires knowledge of all properties to output, and if no definition is found for a property that caller tries to write.
 * QUOTE_FIELD_NAMES
 * Feature that determines whether JSON Object field names are quoted using double-quotes, as specified by JSON specification or not.
 * QUOTE_NON_NUMERIC_NUMBERS
 * Feature that determines whether "exceptional" (not real number) float/double values are output as quoted strings.
 * STRICT_DUPLICATE_DETECTION
 * Feature that determines whether JsonGenerator will explicitly check that no duplicate JSON Object field names are written.
 * WRITE_BIGDECIMAL_AS_PLAIN
 * Feature that determines whether BigDecimal entries are serialized using BigDecimal.toPlainString() to prevent values to be written using scientific notation.
 * WRITE_NUMBERS_AS_STRINGS
 * Feature that forces all Java numbers to be written as JSON strings.
 */
