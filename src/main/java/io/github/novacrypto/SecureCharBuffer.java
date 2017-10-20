package io.github.novacrypto;

import java.io.Closeable;

/**
 * A store of char data that is encrypted with a one-time-pad.
 * Data is pinned outside of garbage collected heap.
 */
public final class SecureCharBuffer implements Closeable, CharSequence {

    /**
     * @param capacity maximum number of chars for buffer to store
     * @return a new {@link SecureCharBuffer} instance
     */
    public static SecureCharBuffer withCapacity(int capacity) {
        return new SecureCharBuffer(capacity);
    }

    private final SecureByteBuffer buffer;

    private SecureCharBuffer(int capacity) {
        buffer = SecureByteBuffer.withCapacity(capacity * 2);
    }

    public SecureCharBuffer() {
        this(512);
    }

    public void append(char c) {
        final byte msb = (byte) (c >> 8);
        final byte lsb = (byte) (c & 0xff);
        buffer.append(msb);
        buffer.append(lsb);
    }

    public void append(final CharSequence data) {
        final int length = data.length();
        for (int i = 0; i < length; i++) {
            append(data.charAt(i));
        }
    }

    public int length() {
        return buffer.length() / 2;
    }

    public char charAt(int index) {
        final int position = index * 2;
        final byte msb = buffer.get(position);
        final byte lsb = buffer.get(position + 1);
        return (char) ((msb << 8) | lsb);
    }

    /**
     * Same as {@link #charAt}, provides indexer syntax in Kotlin
     *
     * @param index 0-based index of char to fetch
     * @return char at that index
     */
    public char get(int index) {
        return charAt(index);
    }

    public CharSequence subSequence(final int start, final int end) {
        if (start == 0 && end == length())
            return this;
        return ProxyCharSequence
                .secureSubSequenceProxy(this,
                        start,
                        end,
                        ToStringStrategy.RESTRICT);
    }

    public CharSequence toStringAble() {
        return ProxyCharSequence
                .secureSubSequenceProxy(this, 0, length(),
                        ToStringStrategy.ALLOW);
    }

    public int capacity() {
        return buffer.capacity() / 2;
    }

    public void close() {
        buffer.close();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}