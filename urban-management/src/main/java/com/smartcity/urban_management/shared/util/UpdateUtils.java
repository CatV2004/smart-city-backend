package com.smartcity.urban_management.shared.util;

import java.util.function.Consumer;

public class UpdateUtils {

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}