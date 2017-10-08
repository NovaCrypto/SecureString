package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aevans on 2017-10-07.
 */
public final class CharSequenceMethodTests {

    @Test
    public void length() {
        final CharSequence charSequence = createFromString("abcd");
        assertEquals(4, charSequence.length());
    }

    @Test
    public void charAt() {
        final CharSequence charSequence = createFromString("abcd");
        assertEquals('a', charSequence.charAt(0));
        assertEquals('b', charSequence.charAt(1));
        assertEquals('c', charSequence.charAt(2));
        assertEquals('d', charSequence.charAt(3));
    }

    private static CharSequence createFromString(String string) {
        final SecureCharBuffer buffer = new SecureCharBuffer();
        buffer.append(string);
        return buffer;
    }
}
