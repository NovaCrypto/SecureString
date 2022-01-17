/*
 *  SecureString library, Obfuscated/clearable in memory string management
 *
 *  Copyright (C) 2017-2022 Alan Evans, NovaCrypto
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *  Original source: https://github.com/NovaCrypto/SecureString
 *  You can contact the authors via github issues.
 */

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
