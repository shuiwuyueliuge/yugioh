package cn.mayu.yugioh.common.basic.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonConstructor {

    private ObjectMapper objectMapper;

    private JsonConstructor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static JsonConstructor defaultInstance() {
        return new JsonConstructor(new ObjectMapper());
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static class JsonConstructorBuilder {

        private ObjectMapper objectMapper;

        public JsonConstructorBuilder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        /**
         * 按字母顺序排序
         */
        public JsonConstructorBuilder sortPropertiesAlphabetically() {
            objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
            return this;
        }

        /**
         * 下划线转驼峰
         */
        public JsonConstructorBuilder namingSnakeCase() {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return this;
        }

        public JsonConstructor build() {
            return new JsonConstructor(this.objectMapper);
        }
    }
}
/**
 * CLOSE_CLOSEABLE
 * Feature that determines whether close method of serialized root level objects (ones for which ObjectMapper's writeValue() (or equivalent) method is called) that implement Closeable is called after serialization or not.
 * EAGER_SERIALIZER_FETCH
 * Feature that determines whether ObjectWriter should try to eagerly fetch necessary JsonSerializer when possible.
 * FAIL_ON_EMPTY_BEANS
 * Feature that determines what happens when no accessors are found for a type (and there are no annotations to indicate it is meant to be serialized).
 * FAIL_ON_SELF_REFERENCES
 * Feature that determines what happens when a direct self-reference is detected by a POJO (and no Object Id handling is enabled for it): either a JsonMappingException is thrown (if true), or reference is normally processed (false).
 * FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS
 * Feature that determines what happens when an object which normally has type information included by Jackson is used in conjunction with JsonUnwrapped.
 * FLUSH_AFTER_WRITE_VALUE
 * Feature that determines whether JsonGenerator.flush() is called after writeValue() method that takes JsonGenerator as an argument completes (i.e.
 * INDENT_OUTPUT
 * Feature that allows enabling (or disabling) indentation for the underlying generator, using the default pretty printer configured for ObjectMapper (and ObjectWriters created from mapper).
 * ORDER_MAP_ENTRIES_BY_KEYS
 * Feature that determines whether Map entries are first sorted by key before serialization or not: if enabled, additional sorting step is performed if necessary (not necessary for SortedMaps), if disabled, no additional sorting is needed.
 * USE_EQUALITY_FOR_OBJECT_ID
 * Feature that determines whether Object Identity is compared using true JVM-level identity of Object (false); or, equals() method.
 * WRAP_EXCEPTIONS
 * Feature that determines whether Jackson code should catch and wrap Exceptions (but never Errors!) to add additional information about location (within input) of problem or not.
 * WRAP_ROOT_VALUE
 * Feature that can be enabled to make root value (usually JSON Object but can be any type) wrapped within a single property JSON object, where key as the "root name", as determined by annotation introspector (esp.
 * WRITE_BIGDECIMAL_AS_PLAIN
 * Deprecated.
 * Since 2.5: use JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN instead (using ObjectWriter.with(com.fasterxml.jackson.core.JsonGenerator.Feature)).
 * WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS
 * Feature that determines how type char[] is serialized: when enabled, will be serialized as an explict JSON array (with single-character Strings as values); when disabled, defaults to serializing them as Strings (which is more compact).
 * WRITE_DATE_KEYS_AS_TIMESTAMPS
 * Feature that determines whether Dates (and sub-types) used as Map keys are serialized as timestamps or not (if not, will be serialized as textual values).
 * WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
 * Feature that controls whether numeric timestamp values are to be written using nanosecond timestamps (enabled) or not (disabled); if and only if datatype supports such resolution.
 * WRITE_DATES_AS_TIMESTAMPS
 * Feature that determines whether Date (and date/time) values (and Date-based things like Calendars) are to be serialized as numeric timestamps (true; the default), or as something else (usually textual representation).
 * WRITE_DATES_WITH_ZONE_ID
 * Feature that determines whether date/date-time values should be serialized so that they include timezone id, in cases where type itself contains timezone information.
 * WRITE_DURATIONS_AS_TIMESTAMPS
 * Feature that determines whether time values that represents time periods (durations, periods, ranges) are to be serialized by default using a numeric (true) or textual (false) representations.
 * WRITE_EMPTY_JSON_ARRAYS
 * Feature that determines whether Container properties (POJO properties with declared value of Collection or array; i.e.
 * WRITE_ENUMS_USING_INDEX
 * Feature that determines whethere Java Enum values are serialized as numbers (true), or textual values (false).
 * WRITE_ENUMS_USING_TO_STRING
 * Feature that determines standard serialization mechanism used for Enum values: if enabled, return value of Enum.toString() is used; if disabled, return value of Enum.name() is used.
 * WRITE_NULL_MAP_VALUES
 * Feature that determines whether Map entries with null values are to be serialized (true) or not (false).
 * WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED
 * Feature added for interoperability, to work with oddities of so-called "BadgerFish" convention.
 */