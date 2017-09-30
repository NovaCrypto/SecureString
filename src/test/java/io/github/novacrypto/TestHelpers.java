package io.github.novacrypto;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

final class TestHelpers {

    private TestHelpers() {
    }

    /**
     * This is just a helper function in tests because using managed array defeats the security benefits of {@link SecureByteBuffer}.
     */
    static byte[] readInToByteArray(final ByteBuffer buffer) {
        final byte[] bytes = new byte[buffer.position()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureByteBuffer}.
     */
    static String readAllAsString(final ByteBuffer buffer) {
        return bytesToString(readAllAsByteArray(buffer));
    }

    static String bytesToString(byte[] bytes) {
        assertEquals("Expect to be even", 0, bytes.length % 2);
        final char[] chars = new char[bytes.length / 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (bytes[i * 2] << 8 | bytes[i * 2 + 1]);
        }
        return new String(chars);
    }

    static byte[] readAllAsByteArray(final ByteBuffer buffer) {
        final byte[] bytes = new byte[buffer.position()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    static byte[] xorDecrypt(final byte[] data, final byte[] key) {
        assertTrue(data.length <= key.length);
        final byte[] bytes = new byte[data.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (data[i] ^ key[i]);
        }
        return bytes;
    }
}
