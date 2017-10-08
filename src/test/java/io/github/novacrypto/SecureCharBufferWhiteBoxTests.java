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
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("plain text");
        assertNotEquals("plain text", asString(getDataBuffer(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsEncryptedWithKey() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("plain text");
        assertEquals("plain text", TestHelpers.asString(xorDecrypt(asByteArray(getDataBuffer(buffer)), asByteArray(getKeyBuffer(buffer)))));
    }

    private static ByteBuffer getDataBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "buffer.data");
    }

    private static ByteBuffer getKeyBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "buffer.key");
    }
}
