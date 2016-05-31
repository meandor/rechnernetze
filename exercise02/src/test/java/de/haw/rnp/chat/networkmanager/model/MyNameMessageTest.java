package de.haw.rnp.chat.networkmanager.model;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class MyNameMessageTest {

    private ChatProtocolMessage message;

    @Before
    public void setUp() throws Exception {
        this.message = new MyNameMessage(InetAddress.getByName("10.0.0.1"), 15000, "FOO");
    }

    @Test
    public void getMessageTest() throws Exception {
        byte[] result = this.message.getFullMessage();
        assertEquals(0x1, result[0]);
        assertEquals(0x4, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x1, result[7]);
        assertEquals(0x3A, result[8]);
        assertEquals(((byte) 0x98), result[9]);
        assertEquals(0x0, result[10]);
        assertEquals(0x1, result[11]);

        assertEquals(0x0, result[12]);
        assertEquals(0x4, result[13]);
        assertEquals(0x0, result[14]);
        assertEquals(0x3, result[15]);
        assertEquals((byte) 0x46, result[16]);
        assertEquals((byte) 0x4f, result[17]);
        assertEquals((byte) 0x4f, result[18]);
    }
}