package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aevans on 2017-10-10.
 */
public final class CharSequenceSubSequenceToStringTests {

    @Test(expected = UnsupportedOperationException.class)
    public void cantToStringOnSubSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        buffer.subSequence(1, 2).toString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantToStringOnSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        buffer.subSequence(1, 5).subSequence(0, 2).toString();
    }

    @Test
    public void canToStringOnSubSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        assertEquals("ovaC",
                buffer.toStringAble()
                        .subSequence(1, 5)
                        .toString());
    }

    @Test
    public void canToStringOnSubSequenceOfSubSequence() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        assertEquals("C",
                buffer.toStringAble()
                        .subSequence(1, 5)
                        .subSequence(3, 4)
                        .toString());
    }
}
