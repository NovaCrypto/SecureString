package io.github.novacrypto;

import org.junit.Test;

import static io.github.novacrypto.TestHelpers.givenSecureCharBuffer;
import static org.junit.Assert.assertEquals;

/**
 * Created by aevans on 2017-10-07.
 */
public final class CharSequenceMethodTests {

    @Test
    public void length() {
        CharSequence charSequence = givenSecureCharBuffer("abcd");
        assertEquals(4, charSequence.length());
    }

    @Test
    public void charAt() {
        CharSequence charSequence = givenSecureCharBuffer("abcd");
        assertEquals('a', charSequence.charAt(0));
        assertEquals('b', charSequence.charAt(1));
        assertEquals('c', charSequence.charAt(2));
        assertEquals('d', charSequence.charAt(3));
    }
}