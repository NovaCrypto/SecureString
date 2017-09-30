package io.github.novacrypto;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * A store of char data that is encrypted with a one-time-pad.
 * Data is pinned outside of garbage collected heap.
 */
final class SecureCharBuffer {

    /**
     * @param capacity maximum number of chars for buffer to store
     * @return a new {@link SecureCharBuffer} instance
     */
    static SecureCharBuffer withCapacity(int capacity) {
        return new SecureCharBuffer(capacity);
    }

    private final ByteBuffer data;
    private final ByteBuffer key;

    private SecureCharBuffer(int capacity) {
        final int byteCapacity = capacity * 2;
        data = allocatePinnedBuffer(byteCapacity);
        key = allocatePinnedBuffer(byteCapacity);
        populateBufferWithSecureKeyData(key);
    }

    /**
     * Direct buffers are outside of garbage collection.
     */
    private static ByteBuffer allocatePinnedBuffer(int byteCapacity) {
        return ByteBuffer.allocateDirect(byteCapacity);
    }

    private static void populateBufferWithSecureKeyData(ByteBuffer key) {
        final Random random = new SecureRandom();
        final byte bytes[] = new byte[key.capacity()];
        random.nextBytes(bytes);
        key.put(bytes);
        Arrays.fill(bytes, (byte) 0);
    }

    SecureCharBuffer() {
        this(512);
    }

    void append(char c) {
        final byte msb = (byte) (c >> 8);
        final byte lsb = (byte) (c & 0xff);
        data.put((byte) (msb ^ key.get(data.position())));
        data.put((byte) (lsb ^ key.get(data.position())));
    }

    int length() {
        return data.position() / 2;
    }

    char get(int i) {
        final int position = i * 2;
        final byte msb = (byte) (data.get(position) ^ key.get(position));
        final byte lsb = (byte) (data.get(position + 1) ^ key.get(position + 1));
        return (char) ((msb << 8) | lsb);
    }

    int capacity() {
        return data.capacity() / 2;
    }
}
