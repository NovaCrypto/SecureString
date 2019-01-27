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

import java.nio.ByteBuffer;

import static io.github.novacrypto.TestHelpers.*;
import static io.github.novacrypto.WhiteBox.getFromPrivateField;
import static org.junit.Assert.*;

public final class SecureByteBufferWhiteBoxTests {

    @Test
    public void whiteBoxTest_keyBufferIsDirect() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        assertTrue(getKeyBuffer(buffer).isDirect());
    }

    @Test
    public void whiteBoxTest_dataBufferIsNotDirect() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        assertTrue(getDataBuffer(buffer).isDirect());
    }

    @Test
    public void whiteBoxTest_dataInBufferIsObscured() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "plain text");
        assertNotEquals("plain text", asString(getDataBuffer(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsEncryptedWithKey() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "plain text");
        assertEquals("plain text",
                asASCIIString(
                        xorDecrypt(
                                asByteArray(getDataBuffer(buffer)),
                                asByteArray(getKeyBuffer(buffer)
                                ))));
    }

    private static ByteBuffer getDataBuffer(SecureByteBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "data");
    }

    private static ByteBuffer getKeyBuffer(SecureByteBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "key");
    }
}