package io.github.novacrypto;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * A store of char data that is encrypted with a one-time-pad.
 * Data is pinned outside of garbage collected heap.
 */
public final class SecureByteBuffer implements Closeable {

    /**
     * @param capacity maximum number of chars for buffer to store
     * @return a new {@link SecureByteBuffer} instance
     */
    public static SecureByteBuffer withCapacity(int capacity) {
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
    private static ByteBuffer allocatePinnedBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity);
    }

    private static void populateBufferWithSecureKeyData(ByteBuffer key) {
        final Random random = new SecureRandom();
        final byte bytes[] = new byte[key.capacity()];
        random.nextBytes(bytes);
        key.put(bytes);
        Arrays.fill(bytes, (byte) 0);
    }

    public SecureByteBuffer() {
        this(1024);
    }

    public void append(byte b) {
        data.put((byte) (b ^ key.get(data.position())));
    }

    public int length() {
        return data.position();
    }

    public byte get(int i) {
        if (i >= length())
            throw new IndexOutOfBoundsException();
        return (byte) (data.get(i) ^ key.get(i));
    }

    public int capacity() {
        return data.capacity();
    }

    public void close() {
        data.position(0);
        key.position(0);
        data.put(key);
    }
}
