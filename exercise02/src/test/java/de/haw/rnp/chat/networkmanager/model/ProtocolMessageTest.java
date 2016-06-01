package de.haw.rnp.chat.networkmanager.model;

import org.junit.Before;
import org.junit.Test;
import util.ChatUtil;
import util.MessageType;

import java.net.InetAddress;
import java.util.*;

import static org.junit.Assert.*;

public class ProtocolMessageTest {
    private ProtocolMessage message;
    byte[] ip;
    byte[] port;
    byte[] string;

    @Before
    public void setUp() throws Exception {
        ip = new byte[]{0x0, 0x1, 0x0, 0x4, 0x7F, 0x0, 0x0, 0x1};
        port = new byte[]{0x0, 0x2, 0x0, 0x2, 0x34, (byte) 0xBC};
        string = new byte[]{0x0, 0x4, 0x0, 0x3, (byte) 0x46, (byte) 0x4f, (byte) 0x4f};
        message = new ProtocolMessage(1, MessageType.Login, InetAddress.getByName("10.0.0.1"), 13000, 3);
        message.addIpField(InetAddress.getByName("127.0.0.1"));
        message.addPortField(13500);
        message.addNameField("FOO");
    }

    @Test
    public void getMessageAsByteArray() throws Exception {
        byte[] result = message.getMessageAsByteArray();
        assertEquals(result.length, 33);
        assertEquals(33, result.length);
        assertEquals(0x1, result[0]);
        assertEquals(0x1, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x1, result[7]);
        assertEquals(0x32, result[8]);
        assertEquals(((byte) 0xC8), result[9]);
        assertEquals(0x0, result[10]);
        assertEquals(0x3, result[11]);

        assertEquals(0x0, result[12]);
        assertEquals(0x1, result[13]);
        assertEquals(0x0, result[14]);
        assertEquals(0x4, result[15]);
        assertEquals(0x7F, result[16]);
        assertEquals(0x0, result[17]);
        assertEquals(0x0, result[18]);
        assertEquals(0x1, result[19]);

        assertEquals(0x0, result[20]);
        assertEquals(0x2, result[21]);
        assertEquals(0x0, result[22]);
        assertEquals(0x2, result[23]);
        assertEquals(0x34, result[24]);
        assertEquals(((byte) 0xBC), result[25]);

        assertEquals(0x0, result[26]);
        assertEquals(0x4, result[27]);
        assertEquals(0x0, result[28]);
        assertEquals(0x3, result[29]);
        assertEquals((byte) 0x46, result[30]);
        assertEquals((byte) 0x4f, result[31]);
        assertEquals((byte) 0x4f, result[32]);
    }

    @Test
    public void getFieldsAsByteList() throws Exception {

        List<byte[]> list = Arrays.asList(ip, port, string);
        assertArrayEquals(message.getFieldsAsByteList().toArray(), list.toArray());
    }

    @Test
    public void getFieldUsername() throws Exception {
        assertEquals("FOO", message.getFieldUsername());
    }

    @Test
    public void getFieldIp() throws Exception {
        InetAddress expected = InetAddress.getByName("127.0.0.1");
        assertEquals(expected, message.getFieldIp());
    }

    @Test
    public void getFieldPort() throws Exception {
        int expected = 13500;
        assertEquals(expected, message.getFieldPort());
    }

    @Test
    public void getFieldText() throws Exception {
        ProtocolMessage result = new ProtocolMessage(1, MessageType.Login, InetAddress.getByName("10.0.0.1"), 13000, 1);
        result.addTextField("FOOBAR");
        assertEquals("FOOBAR", result.getFieldText());
    }

    @Test
    public void byteArrayToUserList() throws Exception {
        HashMap<InetAddress, Integer> userlist = new HashMap<>();
        userlist.put(InetAddress.getByName("10.0.0.1"), 13000);
        userlist.put(InetAddress.getByName("127.0.0.1"), 13500);

        message.addUserListField(userlist);

        assertArrayEquals(userlist.values().toArray(), message.byteArrayToUserList().values().toArray());
    }

}