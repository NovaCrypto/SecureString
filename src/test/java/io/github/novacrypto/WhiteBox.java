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
     */
    static Object getFromPrivateField(final Object object, final String fieldName) {
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
