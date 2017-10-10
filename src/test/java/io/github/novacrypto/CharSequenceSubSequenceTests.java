package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by aevans on 2017-10-08.
 */
public final class CharSequenceSubSequenceTests {

    private static SecureCharBuffer givenBufferContaining(String data) {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append(data);
        return buffer;
    }

    @Test
    public void wholeSequence() {
        final SecureCharBuffer buffer = givenBufferContaining("Hello World!");
        final CharSequence subSequence = buffer.subSequence(0, 12);
        assertSame(buffer, subSequence);
    }

    @Test
    public void wholeSequenceEmpty() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        final CharSequence subSequence = buffer.subSequence(0, 0);
        assertSame(buffer, subSequence);
    }

    @Test
    public void wholeSubSequence() {
        final SecureCharBuffer buffer = givenBufferContaining("Hello World!");
        final CharSequence subSequence = buffer.subSequence(3, 5);
        assertSame(subSequence, subSequence.subSequence(0, 2));
    }

    @Test
    public void subSequence1() {
        final SecureCharBuffer buffer = givenBufferContaining("Hello World!");
        final CharSequence subSequence = buffer.subSequence(6, 11);
        assertEquals("Hello World!".subSequence(6, 11), fromSequence(subSequence));
    }

    @Test
    public void subSequence2() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        assertEquals("NovaCrypto".subSequence(0, 4), fromSequence(subSequence));
    }

    @Test
    public void subSequenceClearsWithParent() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", fromSequence(subSequence));
    }

    @Test
    public void subSequenceOfSubSequence1() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer
                .subSequence(1, 10)
                .subSequence(3, 9);
        assertEquals("NovaCrypto"
                        .subSequence(1, 10)
                        .subSequence(3, 9),
                fromSequence(subSequence));
    }

    @Test
    public void subSequenceOfSubSequence2() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer
                .subSequence(2, 9)
                .subSequence(2, 7);
        assertEquals("NovaCrypto"
                        .subSequence(2, 9)
                        .subSequence(2, 7),
                fromSequence(subSequence));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void subSequenceEndOutOfBounds() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        buffer.subSequence(0, 11);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void subSequenceStartOutOfBounds() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        buffer.subSequence(11, 10);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void startAheadOfEnd() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        buffer.subSequence(5, 4);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void negativeStart() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        buffer.subSequence(-1, 4);
    }

    @Test
    public void afterClose() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", fromSequence(subSequence));
    }

    @Test
    public void afterCloseToStringAble() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer.toStringAble().subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", subSequence.toString());
    }

    @Test
    public void afterCloseSubSequenceOfSubSequence() {
        final String data = "NovaCrypto";
        final SecureCharBuffer buffer = givenBufferContaining(data);
        final CharSequence subSequence = buffer.subSequence(0, 4).subSequence(0, 2);
        buffer.close();
        assertEquals("\0\0", fromSequence(subSequence));
    }

    @Test
    public void afterCloseToStringAbleSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        final CharSequence subSequence = buffer.toStringAble().subSequence(0, 4).subSequence(0, 2);
        buffer.close();
        assertEquals("\0\0", subSequence.toString());
    }

    @Test
    public void zeroChars() {
        final SecureCharBuffer buffer = givenBufferContaining("NovaCrypto");
        assertEquals("NovaCrypto".subSequence(5, 5),
                fromSequence(buffer.subSequence(5, 5)));
    }

    private static String fromSequence(final CharSequence subSequence) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subSequence.length(); i++) {
            sb.append(subSequence.charAt(i));
        }
        return sb.toString();
    }
}