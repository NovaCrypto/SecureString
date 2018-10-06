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

import static io.github.novacrypto.TestHelpers.asString;
import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;
import static org.junit.Assert.assertEquals;

/**
 * Created by aevans on 2017-10-10.
 */
public final class CharSequenceSubSequenceToStringTests {

    @Test(expected = UnsupportedOperationException.class)
    public void cantToStringOnSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        buffer.subSequence(1, 2).toString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantToStringOnSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        buffer.subSequence(1, 5).subSequence(0, 2).toString();
    }

    @Test
    public void canToStringOnSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        assertEquals("ovaC",
                buffer.toStringAble()
                        .subSequence(1, 5)
                        .toString());
    }

    @Test
    public void canToStringOnSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        assertEquals("C",
                buffer.toStringAble()
                        .subSequence(1, 5)
                        .subSequence(3, 4)
                        .toString());
    }

    @Test
    public void afterClose() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", asString(subSequence));
    }

    @Test
    public void afterCloseToStringAble() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        final CharSequence subSequence = buffer.toStringAble().subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", subSequence.toString());
    }

    @Test
    public void afterCloseSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4).subSequence(0, 2);
        buffer.close();
        assertEquals("\0\0", asString(subSequence));
    }

    @Test
    public void afterCloseToStringAbleSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        final CharSequence subSequence = buffer.toStringAble().subSequence(0, 4).subSequence(0, 2);
        buffer.close();
        assertEquals("\0\0", subSequence.toString());
    }
}