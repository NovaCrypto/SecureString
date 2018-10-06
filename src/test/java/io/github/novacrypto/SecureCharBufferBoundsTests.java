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

public final class SecureCharBufferBoundsTests {

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAtLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAboveLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(buffer.length() + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtAboveLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(buffer.length() + 1);
    }
}