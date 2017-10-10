package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.appendASCIIString;

/**
 * Created by aevans on 2017-10-10.
 */
public final class SecureByteBufferBoundsTests {

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative1() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative2() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAtLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAboveLength() {
        SecureByteBuffer buffer = new SecureByteBuffer();
        appendASCIIString(buffer, "abc");
        buffer.get(buffer.length() + 1);
    }
}