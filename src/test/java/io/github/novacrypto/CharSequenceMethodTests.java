/*
 *  SecureString library, Obfuscated/clearable in memory string management
 *  Copyright (C) 2017-2018 Alan Evans, NovaCrypto
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

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;
import static org.junit.Assert.assertEquals;

public final class CharSequenceMethodTests {

    @Test
    public void length() {
        CharSequence charSequence = givenSecureCharBuffer("abcd");
        assertEquals(4, charSequence.length());
    }

    @Test
    public void charAt() {
        CharSequence charSequence = givenSecureCharBuffer("abcd");
        assertEquals('a', charSequence.charAt(0));
        assertEquals('b', charSequence.charAt(1));
        assertEquals('c', charSequence.charAt(2));
        assertEquals('d', charSequence.charAt(3));
    }
}