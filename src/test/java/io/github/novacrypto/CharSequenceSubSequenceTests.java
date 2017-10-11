package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.asString;
import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by aevans on 2017-10-08.
 */
public final class CharSequenceSubSequenceTests {

    @Test
    public void wholeSequence() {
        SecureCharBuffer buffer = givenSecureCharBuffer("Hello World!");
        CharSequence subSequence = buffer.subSequence(0, 12);
        assertSame(buffer, subSequence);
    }

    @Test
    public void wholeSequenceEmpty() {
        SecureCharBuffer buffer = new SecureCharBuffer();
        CharSequence subSequence = buffer.subSequence(0, 0);
        assertSame(buffer, subSequence);
    }

    @Test
    public void wholeSubSequence() {
        SecureCharBuffer buffer = givenSecureCharBuffer("Hello World!");
        CharSequence subSequence = buffer.subSequence(3, 5);
        assertSame(subSequence, subSequence.subSequence(0, 2));
    }

    @Test
    public void subSequence1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("Hello World!");
        CharSequence subSequence = buffer.subSequence(6, 11);
        assertEquals("Hello World!".subSequence(6, 11), asString(subSequence));
    }

    @Test
    public void subSequence2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(0, 4);
        assertEquals("NovaCrypto".subSequence(0, 4), asString(subSequence));
    }

    @Test
    public void subSequenceClearsWithParent() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer.subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", asString(subSequence));
    }

    @Test
    public void subSequenceOfSubSequence1() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer
                .subSequence(1, 10)
                .subSequence(3, 9);
        assertEquals("NovaCrypto"
                        .subSequence(1, 10)
                        .subSequence(3, 9),
                asString(subSequence));
    }

    @Test
    public void subSequenceOfSubSequence2() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        CharSequence subSequence = buffer
                .subSequence(2, 9)
                .subSequence(2, 7);
        assertEquals("NovaCrypto"
                        .subSequence(2, 9)
                        .subSequence(2, 7),
                asString(subSequence));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void subSequenceEndOutOfBounds() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        buffer.subSequence(0, 11);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void subSequenceStartOutOfBounds() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        buffer.subSequence(11, 10);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void startAheadOfEnd() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        buffer.subSequence(5, 4);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void negativeStart() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        buffer.subSequence(-1, 4);
    }

    @Test
    public void zeroChars() {
        SecureCharBuffer buffer = givenSecureCharBuffer("NovaCrypto");
        assertEquals("NovaCrypto".subSequence(5, 5),
                asString(buffer.subSequence(5, 5)));
    }
}