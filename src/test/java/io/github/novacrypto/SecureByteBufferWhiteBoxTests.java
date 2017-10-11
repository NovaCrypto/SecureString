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