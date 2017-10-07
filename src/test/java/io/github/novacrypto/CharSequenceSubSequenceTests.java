package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by aevans on 2017-10-08.
 */
public final class CharSequenceSubSequenceTests {

    @Test
    public void wholeSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("Hello World!");
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
    public void subSequence1() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("Hello World!");
        final CharSequence subSequence = buffer.subSequence(6, 11);
        assertEquals("Hello World!".subSequence(6, 11), fromSequence(subSequence));
    }

    @Test
    public void subSequence2() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        assertEquals("NovaCrypto".subSequence(0, 4), fromSequence(subSequence));
    }


    @Test
    public void subSequenceClearsWithParent() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        final CharSequence subSequence = buffer.subSequence(0, 4);
        buffer.close();
        assertEquals("\0\0\0\0", fromSequence(subSequence));
    }

    @Test
    public void subSequenceOfSubSequence1() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
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
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
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
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.subSequence(0, 11);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void subSequenceStartOutOfBounds() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.subSequence(11, 10);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void startAheadOfEnd() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.subSequence(5, 4);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void negativeStart() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.subSequence(-1, 4);
    }

    @Test
    public void zeroChars() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        assertEquals("NovaCrypto".subSequence(5, 5),
                fromSequence(buffer.subSequence(5, 5)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantToString() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        buffer.toString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantToStringOnSubSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.subSequence(1, 2).toString();
    }

    private static String fromSequence(final CharSequence subSequence) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subSequence.length(); i++) {
            sb.append(subSequence.charAt(i));
        }
        return sb.toString();
    }


}
