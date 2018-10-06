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

import static io.github.novacrypto.TestHelpers.appendASCIIString;

/**
 * Created by aevans on 2017-10-10.
 */
public final class SecureByteBufferBoundsTests {

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative1() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative2() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAtLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAboveLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(buffer.length() + 1);
    }
}