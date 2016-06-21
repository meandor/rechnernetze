package de.haw.rnp.util;

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the AddressType
 *
 * @author Daniel Schruhl
 */
public class AddressTypeTest {

    @org.junit.Test
    public void equals() throws Exception {
        AddressType a1 = new AddressType("10.0.0.1", 1);
        AddressType a2 = new AddressType(InetAddress.getByName("10.0.0.1"), 1);
        AddressType a3 = new AddressType("10.0.0.3", 1);
        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertNotEquals(a1, null);
        assertNotEquals(a1, a3);
    }
}