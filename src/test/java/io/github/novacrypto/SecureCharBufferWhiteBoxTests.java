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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public final class SecureCharBufferWhiteBoxTests {

    @Test
    public void whiteBoxTest_dataInBufferIsObscured() {
        SecureCharBuffer buffer = givenSecureCharBuffer("plain text");
        assertNotEquals("plain text", asString(getDataBuffer(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsEncryptedWithKey() {
        SecureCharBuffer buffer = givenSecureCharBuffer("plain text");
        assertEquals("plain text",
                TestHelpers.asString(
                        xorDecrypt(asByteArray(getDataBuffer(buffer)),
                                asByteArray(getKeyBuffer(buffer)))));
    }

    private static ByteBuffer getDataBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "buffer.data");
    }

    private static ByteBuffer getKeyBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "buffer.key");
    }
}