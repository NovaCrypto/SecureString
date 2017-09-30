package io.github.novacrypto;

import org.junit.Test;

import java.nio.ByteBuffer;

import static io.github.novacrypto.TestHelpers.*;
import static io.github.novacrypto.WhiteBox.getFromPrivateField;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    public void overrideCapacity() {
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
        assertEquals("abc", bytesToASCIIString(readWholeBufferAsByteArray(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsObscured() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "plain text");
        assertNotEquals("plain text", readAllAsString(getDataBuffer(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsEncryptedWithKey() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "plain text");
        assertEquals("plain text",
                bytesToASCIIString(
                        xorDecrypt(
                                readInToByteArray(getDataBuffer(buffer)),
                                readInToByteArray(getKeyBuffer(buffer)
                                ))));
    }

    private static ByteBuffer getDataBuffer(SecureByteBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "data");
    }

    private static ByteBuffer getKeyBuffer(SecureByteBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "key");
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureByteBuffer}.
     */
    private static void appendASCIIString(final SecureByteBuffer buffer, final CharSequence data) {
        final int length = data.length();
        for (int i = 0; i < length; i++) {
            buffer.append((byte) (data.charAt(i)));
        }
    }

    private static String bytesToASCIIString(byte[] bytes) {
        final char[] chars = new char[bytes.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (bytes[i]);
        }
        return new String(chars);
    }

    private static byte[] readWholeBufferAsByteArray(final SecureByteBuffer buffer) {
        final byte[] bytes = new byte[buffer.length()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }


}
