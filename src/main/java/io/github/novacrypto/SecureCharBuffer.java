package io.github.novacrypto;

import java.io.Closeable;

/**
 * A store of char data that is encrypted with a one-time-pad.
 * Data is pinned outside of garbage collected heap.
 */
final class SecureCharBuffer implements Closeable {

    /**
     * @param capacity maximum number of chars for buffer to store
     * @return a new {@link SecureCharBuffer} instance
     */
    static SecureCharBuffer withCapacity(int capacity) {
        return new SecureCharBuffer(capacity);
    }

    private final SecureByteBuffer buffer;

    private SecureCharBuffer(int capacity) {
        buffer = SecureByteBuffer.withCapacity(capacity * 2);
    }

    SecureCharBuffer() {
        this(512);
    }

    void append(char c) {
        final byte msb = (byte) (c >> 8);
        final byte lsb = (byte) (c & 0xff);
        buffer.append(msb);
        buffer.append(lsb);
    }

    int length() {
        return buffer.length() / 2;
    }

    char get(int i) {
        final int position = i * 2;
        final byte msb = buffer.get(position);
        final byte lsb = buffer.get(position + 1);
        return (char) ((msb << 8) | lsb);
    }

    int capacity() {
        return buffer.capacity() / 2;
    }

    public void close() {
        buffer.close();
    }
}
