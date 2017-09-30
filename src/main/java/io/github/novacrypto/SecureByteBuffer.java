package io.github.novacrypto;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * A store of char data that is encrypted with a one-time-pad.
 * Data is pinned outside of garbage collected heap.
 */
final class SecureByteBuffer {

    /**
     * @param capacity maximum number of chars for buffer to store
     * @return a new {@link SecureByteBuffer} instance
     */
    static SecureByteBuffer withCapacity(int capacity) {
        return new SecureByteBuffer(capacity);
    }

    private final ByteBuffer data;
    private final ByteBuffer key;

    private SecureByteBuffer(int capacity) {
        data = allocatePinnedBuffer(capacity);
        key = allocatePinnedBuffer(capacity);
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

    SecureByteBuffer() {
        this(1024);
    }

    void append(byte b) {
        data.put((byte) (b ^ key.get(data.position())));
    }

    int length() {
        return data.position();
    }

    byte get(int i) {
        return  (byte) (data.get(i) ^ key.get(i));
    }

    int capacity() {
        return data.capacity();
    }
}
