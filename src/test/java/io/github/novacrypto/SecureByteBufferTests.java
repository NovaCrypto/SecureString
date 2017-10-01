package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.*;
import static org.junit.Assert.assertEquals;

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
        assertEquals("abc", asASCIIString(readWholeBufferAsByteArray(buffer)));
    }

    @Test
    public void dataIsZeroedOutAfterClose() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.close();
        final byte[] read = readWholeBufferAsByteArray(buffer);
        assertEquals(1024, buffer.length());
        assertEquals(1024, read.length);
        for (byte b : read) {
            assertEquals(0, b);
        }
    }
}
