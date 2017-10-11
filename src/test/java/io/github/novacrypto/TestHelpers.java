package io.github.novacrypto;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

final class TestHelpers {

    private TestHelpers() {
    }

    static SecureCharBuffer givenSecureCharBuffer(String data) {
        SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append(data);
        return buffer;
    }

    /**
     * This is just a helper function in tests because using managed array defeats the security benefits of {@link SecureByteBuffer}.
     */
    static byte[] asByteArray(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.position()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureByteBuffer}.
     */
    static String asString(ByteBuffer buffer) {
        return asString(asByteArray(buffer));
    }

    /**
     * Reads bytes in pairs, so contains full width {@link char} data.
     */
    static String asString(byte[] bytes) {
        assertEquals("Expect to be even", 0, bytes.length % 2);
        char[] chars = new char[bytes.length / 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (bytes[i * 2] << 8 | bytes[i * 2 + 1]);
        }
        return new String(chars);
    }

    static String asString(CharSequence subSequence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subSequence.length(); i++) {
            sb.append(subSequence.charAt(i));
        }
        return sb.toString();
    }

    static byte[] xorDecrypt(byte[] data, byte[] key) {
        assertTrue(data.length <= key.length);
        byte[] bytes = new byte[data.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (data[i] ^ key[i]);
        }
        return bytes;
    }

    /**
     * Write the least significant byte of each {@link char} to the buffer.
     * If the string is just ASCII, this will not lose any data.
     */
    static void appendASCIIString(SecureByteBuffer buffer, CharSequence data) {
        int length = data.length();
        for (int i = 0; i < length; i++) {
            buffer.append((byte) (data.charAt(i)));
        }
    }

    /**
     * Read bytes as least significant chars of a {@link char}.
     */
    static String asASCIIString(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (bytes[i]);
        }
        return new String(chars);
    }

    static byte[] readWholeBufferAsByteArray(SecureByteBuffer buffer) {
        byte[] bytes = new byte[buffer.length()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    /**
     * This is just a helper function in tests because using String defeats the security benefits of {@link SecureCharBuffer}.
     */
    static String readWholeBufferAsString(SecureCharBuffer buffer) {
        char[] chars = new char[buffer.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = buffer.get(i);
        }
        return new String(chars);
    }
}