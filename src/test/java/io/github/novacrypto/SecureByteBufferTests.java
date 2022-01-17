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

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.*;
import static org.junit.Assert.assertEquals;

public final class SecureByteBufferTests {

    @Test
    public void initialLengthIsZero() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        assertEquals(0, buffer.length());
    }

    @Test
    public void defaultCapacity() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        assertEquals(1024, buffer.capacity());
    }

    @Test
    public void canSpecifyCapacity() {
        SecureByteBuffer buffer = SecureByteBuffer.withCapacity(10);
        assertEquals(10, buffer.capacity());
    }

    @Test
    public void capacityIsNumberOfBytes() {
        SecureByteBuffer buffer = SecureByteBuffer.withCapacity(1);
        buffer.append((byte) 1);
    }

    @Test
    public void appendIncreasesLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        buffer.append((byte) 1);
        assertEquals(1, buffer.length());
    }

    @Test
    public void twoAppendsIncreaseLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        buffer.append((byte) 1);
        buffer.append((byte) 2);
        assertEquals(2, buffer.length());
    }

    @Test
    public void afterAppendCanReadBufferContents() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        buffer.append((byte) 10);
        assertEquals(1, buffer.length());
        assertEquals(10, buffer.get(0));
    }

    @Test
    public void afterAppendCanReadBufferContentsZ() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        buffer.append((byte) 26);
        assertEquals(1, buffer.length());
        assertEquals(26, buffer.get(0));
    }

    @Test
    public void canReadManyChars() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        assertEquals("abc", asASCIIString(readWholeBufferAsByteArray(buffer)));
    }

    @Test
    public void dataIsZeroedOutAfterClose() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.close();
        byte[] read = readWholeBufferAsByteArray(buffer);
        assertEquals(1024, buffer.length());
        assertEquals(1024, read.length);
        for (byte b : read) {
            assertEquals(0, b);
        }
    }
}