/*
 *  SecureString library, Obfuscated/clearable in memory string management
 *  Copyright (C) 2017-2019 Alan Evans, NovaCrypto
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

import static io.github.novacrypto.TestHelpers.readWholeBufferAsString;
import static org.junit.Assert.assertEquals;

public final class SecureCharBufferTests {

    @Test
    public void initialLengthIsZero() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        assertEquals(0, buffer.length());
    }

    @Test
    public void defaultCapacity() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        assertEquals(512, buffer.capacity());
    }

    @Test
    public void canSpecifyCapacity() {
        SecureCharBuffer buffer = SecureCharBuffer.withCapacity(10);
        assertEquals(10, buffer.capacity());
    }

    @Test
    public void capacityIsNumberOfChars() {
        SecureCharBuffer buffer = SecureCharBuffer.withCapacity(1);
        buffer.append('a');
    }

    @Test
    public void appendIncreasesLength() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        assertEquals(1, buffer.length());
    }

    @Test
    public void twoAppendsIncreaseLength() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        buffer.append('b');
        assertEquals(2, buffer.length());
    }

    @Test
    public void afterAppendCanReadBufferContents() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('a');
        assertEquals(1, buffer.length());
        final int i = 0;
        assertEquals('a', readCharAt(buffer, i));
    }

    @Test
    public void afterAppendCanReadBufferContentsZ() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('z');
        assertEquals(1, buffer.length());
        assertEquals('z', readCharAt(buffer, 0));
    }

    @Test
    public void canAppendCharSequences() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("abc");
        final CharSequence def = "def";
        buffer.append(def);
        assertEquals("abcdef", readWholeBufferAsString(buffer));
    }

    @Test
    public void canReadManyChars() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("abc");
        assertEquals("abc", readWholeBufferAsString(buffer));
    }

    @Test
    public void dataIsZeroedOutAfterClose() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("abc");
        buffer.close();
        final String read = readWholeBufferAsString(buffer);
        assertEquals(512, buffer.length());
        assertEquals(512, read.length());
        for (int i = 0; i < read.length(); i++) {
            assertEquals('\0', read.charAt(i));
        }
    }

    private static char readCharAt(SecureCharBuffer buffer, int index) {
        final char c1 = buffer.get(index);
        final char c2 = buffer.charAt(index);
        assertEquals(c1, c2);
        return c1;
    }
}