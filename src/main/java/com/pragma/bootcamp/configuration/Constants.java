package com.pragma.bootcamp.configuration;

public final class Constants {

    private Constants()  {
        throw new IllegalStateException("Utility class");
    }


    //REGULAR EXPRESSION
    public static final String PATTERN_NAME = "^[A-Za-z+#\\s]+$";
    public static final String PATTERN_DESCRIPTION = "^[A-Za-z0-9+#\\sáéíóúÁÉÍÓÚñÑ]+$";

    //______________________________________________________________________________________________

    public enum Field {
        NOMBRE,
        ID;
    }

    //KEY MESSAGE
    public static final String DEFAULT_MESSAGE = "error.default.message";
    public static final String FIELD_EMPTY_MESSAGE = "empty.field.message";
    public static final String SPECIAL_CHARACTER_MESSAGE = "special.character.message";
    public static final String NAME_SIZE_TECHNOLOGY_MESSAGE = "name.size.technology.message";
    public static final String NAME_SIZE_CAPACITY_MESSAGE = "name.size.capacity.message";
    public static final String NAME_SIZE_BOOTCAMP_MESSAGE = "name.size.bootcamp.message";
    public static final String DESCRIPTION_SIZE_MESSAGE = "description.size.message";
    public static final String PAGE_INVALID_MESSAGE = "page.invalid.message";
    public static final String SIZE_INVALID_MESSAGE = "size.invalid.message";
    public static final String NOT_FOUND_BOOTCAMP_MESSAGE = "error.not.found.bootcamp.message";
    public static final String FIELD_NOT_NULL_MESSAGE = "field.notNull.message";
    public static final String INVALID_FORMAT_TO_PARSE_DATE = "invalid.format.date.message";
    public static final String MINIMUM_CAPACITY_MESSAGE = "minimum.capacity.message";
    public static final String ACCESS_DENIED_MESSAGE = "Access Denied";
    public static final String PERMISSIONS_ACCESS_DENIED_MESSAGE = "Don´t have permissions to access this endpoint";
}
