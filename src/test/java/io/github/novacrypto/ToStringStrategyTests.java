package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aevans on 2017-10-11.
 */
public final class ToStringStrategyTests {

    @Test
    public void values() {
        ToStringStrategy[] values = ToStringStrategy.values();
        assertEquals(2, values.length);
        assertEquals(ToStringStrategy.RESTRICT, values[0]);
        assertEquals(ToStringStrategy.ALLOW, values[1]);
    }

    @Test
    public void valueOfRestrict() {
        ToStringStrategy values = ToStringStrategy.valueOf("RESTRICT");
        assertEquals(ToStringStrategy.RESTRICT, values);
    }

    @Test
    public void valueOfAllow() {
        ToStringStrategy values = ToStringStrategy.valueOf("ALLOW");
        assertEquals(ToStringStrategy.ALLOW, values);
    }
}