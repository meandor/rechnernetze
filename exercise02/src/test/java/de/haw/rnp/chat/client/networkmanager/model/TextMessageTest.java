package de.haw.rnp.chat.client.networkmanager.model;

import de.haw.rnp.chat.model.User;
import org.junit.Before;
import org.junit.Test;
import util.Triplet;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TextMessageTest {

    private ChatProtocolMessage message;

    @Before
    public void setUp() throws Exception {
        User u1 = new User("", 15500, InetAddress.getByName("10.0.0.1"));
        User u2 = new User("", 15000, InetAddress.getByName("10.0.0.2"));
        ArrayList<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        this.message = new TextMessage(InetAddress.getByName("10.0.0.1"), 15000, "FOO", userList);
    }

    @Test
    public void getMessageTest() throws Exception {
        byte[] result = this.message.getFullMessage();
        assertEquals(39, result.length);
        assertEquals(0x1, result[0]);
        assertEquals(0x3, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x1, result[7]);
        assertEquals(0x3A, result[8]);
        assertEquals(((byte) 0x98), result[9]);
        assertEquals(0x0, result[10]);
        assertEquals(0x2, result[11]);

        assertEquals(0x0, result[12]);
        assertEquals(0x5, result[13]);
        assertEquals(0x0, result[14]);
        assertEquals(0x3, result[15]);
        assertEquals((byte) 0x46, result[16]);
        assertEquals((byte) 0x4f, result[17]);
        assertEquals((byte) 0x4f, result[18]);

        assertEquals(0x0, result[19]);
        assertEquals(0x3, result[20]);
        assertEquals(0x0, result[21]);
        assertEquals((byte) 0x10, result[22]);

        assertEquals(0xA, result[23]);
        assertEquals(0x0, result[24]);
        assertEquals(0x0, result[25]);
        assertEquals(0x1, result[26]);

        assertEquals(0x0, result[27]);
        assertEquals(0x0, result[28]);
        assertEquals((byte) 0x3C, result[29]);
        assertEquals(((byte) 0x8c), result[30]);

        assertEquals(0xA, result[31]);
        assertEquals(0x0, result[32]);
        assertEquals(0x0, result[33]);
        assertEquals(0x2, result[34]);

        assertEquals(0x0, result[35]);
        assertEquals(0x0, result[36]);
        assertEquals(0x3A, result[37]);
        assertEquals(((byte) 0x98), result[38]);
    }

    @Test
    public void testUserList() throws Exception {
        ProtocolMessage p = new ProtocolMessage(this.message.getFullMessage());
        assertNull(p.getFieldIp());
        assertNotNull(p.byteArrayToUserList());
        assertEquals(15500, p.byteArrayToUserList().values().toArray()[0]);
        assertEquals(15000, p.byteArrayToUserList().values().toArray()[1]);
        assertEquals(InetAddress.getByName("10.0.0.1"), p.byteArrayToUserList().keySet().toArray()[0]);
        assertEquals(InetAddress.getByName("10.0.0.2"), p.byteArrayToUserList().keySet().toArray()[1]);
    }
}