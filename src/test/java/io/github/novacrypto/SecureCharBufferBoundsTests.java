package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;

/**
 * Created by aevans on 2017-10-10.
 */
public final class SecureCharBufferBoundsTests {

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegative2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAtLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAboveLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.get(buffer.length() + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(buffer.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtAboveLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("abc");
        buffer.charAt(buffer.length() + 1);
    }
}