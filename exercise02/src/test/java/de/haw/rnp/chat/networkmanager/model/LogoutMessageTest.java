package de.haw.rnp.chat.networkmanager.model;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class LogoutMessageTest {

    private ChatProtocolMessage message;

    @Before
    public void setUp() throws Exception {
        this.message = new LogoutMessage(InetAddress.getByName("10.0.0.5"), 15000, InetAddress.getByName("10.0.0.1"), 15500);
    }

    @Test
    public void getFullMessageTest() throws Exception {
        byte[] result = this.message.getFullMessage();
        assertEquals(26, result.length);
        assertEquals(0x1, result[0]);
        assertEquals(0x2, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x5, result[7]);
        assertEquals(0x3A, result[8]);
        assertEquals(((byte) 0x98), result[9]);
        assertEquals(0x0, result[10]);
        assertEquals(0x2, result[11]);

        assertEquals(0x0, result[12]);
        assertEquals(0x1, result[13]);
        assertEquals(0x0, result[14]);
        assertEquals(0x4, result[15]);
        assertEquals(0xA, result[16]);
        assertEquals(0x0, result[17]);
        assertEquals(0x0, result[18]);
        assertEquals(0x1, result[19]);

        assertEquals(0x0, result[20]);
        assertEquals(0x2, result[21]);
        assertEquals(0x0, result[22]);
        assertEquals(0x2, result[23]);
        assertEquals(0x3C, result[24]);
        assertEquals(((byte) 0x8C), result[25]);
    }
}