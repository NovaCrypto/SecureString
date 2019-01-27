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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public final class ToStringTests {

    @Test(expected = UnsupportedOperationException.class)
    public void cantToString() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        String s = buffer.toString();
    }

    @Test
    public void canToStringIfMadeToStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer.toStringAble();
        assertEquals("NovaCrypto", sequence.toString());
    }

    @Test
    public void canToStringIfAppendedTwiceAndMadeToStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.append("!");
        CharSequence sequence = buffer.toStringAble();
        assertEquals("NovaCrypto!", sequence.toString());
    }

    @Test
    public void toStringingTwiceDoesNotReturnsTheSameEnsuringNotCached() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer.toStringAble();
        assertNotSame(sequence.toString(), sequence.toString());
    }

    @Test
    public void toStringingAfterCloseDoesNotReturnsTheStringEnsuringCalculatedOnDemand() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer.toStringAble();
        final String first = sequence.toString();
        buffer.close();
        assertEquals("NovaCrypto", first);
        assertEquals("\0\0\0\0\0\0\0\0\0\0", sequence.toString());
    }

    @Test
    public void subBufferOfStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer
                .toStringAble()
                .subSequence(4, 9);
        assertEquals("Crypt", sequence.toString());
    }

    @Test
    public void subBufferOfSubBufferStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer
                .toStringAble()
                .subSequence(4, 9)
                .subSequence(0, 3);
        assertEquals("Cry", sequence.toString());
    }
}