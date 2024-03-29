/*
 *  SecureString library, Obfuscated/clearable in memory string management
 *
 *  Copyright (C) 2017-2022 Alan Evans, NovaCrypto
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *  Original source: https://github.com/NovaCrypto/SecureString
 *  You can contact the authors via github issues.
 */

package io.github.novacrypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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