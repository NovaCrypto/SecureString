package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;

/**
 * Created by aevans on 2017-10-10.
 */
public final class CharSequenceSubSequenceBoundsTests {

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(1, 4);
        subSequence.charAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtNegative2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(3, 8);
        subSequence.charAt(-2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(1, 4);
        subSequence.charAt(subSequence.length());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void charAtAboveLength() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(1, 4);
        subSequence.charAt(subSequence.length() + 1);
    }
}