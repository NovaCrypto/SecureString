package io.github.novacrypto;

import org.junit.Test;

import java.nio.ByteBuffer;

import static io.github.novacrypto.WhiteBox.getFromPrivateField;
import static org.junit.Assert.*;

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
    public void overrideCapacity() {
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
        assertEquals('a', buffer.get(0));
    }

    @Test
    public void afterAppendCanReadBufferContentsZ() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append('z');
        assertEquals(1, buffer.length());
        assertEquals('z', buffer.get(0));
    }

    @Test
    public void canReadManyChars() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        appendString(buffer, "abc");
        assertEquals("abc", readInToString(buffer));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsObscured() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        appendString(buffer, "plain text");
        assertNotEquals("plain text", readInToString(getDataBuffer(buffer)));
    }

    @Test
    public void whiteBoxTest_dataInBufferIsEncryptedWithKey() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        appendString(buffer, "plain text");
        assertEquals("plain text", bytesToString(xorDecrypt(readInToByteArray(getDataBuffer(buffer)), readInToByteArray(getKeyBuffer(buffer)))));
    }


    private static ByteBuffer getDataBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "data");
    }

    private static ByteBuffer getKeyBuffer(SecureCharBuffer buffer) {
        return (ByteBuffer) getFromPrivateField(buffer, "key");
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureCharBuffer}.
     */
    private static void appendString(final SecureCharBuffer buffer, final CharSequence data) {
        final int length = data.length();
        for (int i = 0; i < length; i++) {
            buffer.append(data.charAt(i));
        }
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureCharBuffer}.
     */
    private static String readInToString(final SecureCharBuffer buffer) {
        final char[] chars = new char[buffer.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = buffer.get(i);
        }
        return new String(chars);
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureCharBuffer}.
     */
    private static String readInToString(final ByteBuffer buffer) {
        final byte[] bytes = readInToByteArray(buffer);
        return bytesToString(bytes);
    }

    private static String bytesToString(byte[] bytes) {
        assertEquals("Expect to be even", 0, bytes.length % 2);
        final char[] chars = new char[bytes.length / 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (bytes[i * 2] << 8 | bytes[i * 2 + 1]);
        }
        return new String(chars);
    }

    private static byte[] readInToByteArray(final ByteBuffer buffer) {
        final byte[] bytes = new byte[buffer.position()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    private static byte[] xorDecrypt(final byte[] data, final byte[] key) {
        assertTrue(data.length <= key.length);
        final byte[] bytes = new byte[data.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (data[i] ^ key[i]);
        }
        return bytes;
    }

}
