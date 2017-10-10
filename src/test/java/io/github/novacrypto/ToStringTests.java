package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by aevans on 2017-10-10.
 */
public final class ToStringTests {

    @Test(expected = UnsupportedOperationException.class)
    public void cantToString() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        //noinspection ResultOfMethodCallIgnored
        String s = buffer.toString();
    }

    @Test
    public void canToStringIfMadeToStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer.toStringAble();
        assertEquals("NovaCrypto", sequence.toString());
    }

    @Test
    public void canToStringIfAppendedTwiceAndMadeToStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        buffer.append("!");
        CharSequence sequence = buffer.toStringAble();
        assertEquals("NovaCrypto!", sequence.toString());
    }

    @Test
    public void toStringingTwiceDoesNotReturnsTheSameEnsuringCalculatedOnDemand() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer.toStringAble();
        assertNotSame(sequence.toString(), sequence.toString());
    }

    @Test
    public void subBufferOfStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer
                .toStringAble()
                .subSequence(4, 9);
        assertEquals("Crypt", sequence.toString());
    }

    @Test
    public void subBufferOfSubBufferStringAble() {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append("NovaCrypto");
        CharSequence sequence = buffer
                .toStringAble()
                .subSequence(4, 9)
                .subSequence(0, 3);
        assertEquals("Cry", sequence.toString());
    }
}