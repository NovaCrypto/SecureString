package io.github.novacrypto;

import java.lang.reflect.Field;

/**
 * Reflection helpers for white-box tests.
 */
final class WhiteBox {

    private WhiteBox() {
    }

    /**
     * Finds the contents of a private field.
     * If path includes one of more '.' will do so recursively.
     * Restores fields accessible state.
     */
    static Object getFromPrivateField(final Object object, final String fieldNamePath) {
        Object result = object;
        for (String fieldName : fieldNamePath.split("\\.")) {
            result = getPrivateFieldValue(result, fieldName);
        }
        return result;
    }

    private static Object getPrivateFieldValue(final Object object, final String fieldName) {
        try {
            final Field declaredField = object.getClass().getDeclaredField(fieldName);
            final boolean wasAccessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            try {
                return declaredField.get(object);
            } finally {
                declaredField.setAccessible(wasAccessible);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
