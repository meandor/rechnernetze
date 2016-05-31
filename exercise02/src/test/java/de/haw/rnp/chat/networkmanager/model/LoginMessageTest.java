package de.haw.rnp.chat.networkmanager.model;

import org.junit.Before;
import org.junit.Test;
import util.MessageType;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class LoginMessageTest {

    private ChatProtocolMessage message;
    private ProtocolMessage message2;

    @Before
    public void setUp() throws Exception {
        this.message = new LoginMessage(InetAddress.getByName("10.0.0.1"), 13000, "FOO", InetAddress.getByName("127.0.0.1"), 13500);
        //this.message2 = new ProtocolMessage(1, MessageType.Login, InetAddress.getByName("10.0.0.1"), 13000, 3);
        //this.message2 = new ProtocolMessage(message.getFullMessage());
    }

    @Test
    public void getFullMessageTest() throws Exception {
        byte[] result = this.message.getFullMessage();
        /*byte[] result2 = this.message2.getMessageAsByteArray();
        for(int x = 0; x < result.length; x++){
            System.out.println("...........");
            System.out.println(result[x]);
           //System.out.println(result2[x]);
            System.out.println("counter " + x);
        }*/
        //assertArrayEquals(result, message2.getMessageAsByteArray());
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
    public void intToByteArray() throws Exception {
        byte[] result = this.message.intToByteArray(1);
        assertEquals((byte) 0x00, result[0]);
        assertEquals((byte) 0x00, result[1]);
        assertEquals((byte) 0x00, result[2]);
        assertEquals((byte) 0x01, result[3]);
        result = this.message.intToByteArray(15);
        assertEquals((byte) 0x00, result[0]);
        assertEquals((byte) 0x00, result[1]);
        assertEquals((byte) 0x00, result[2]);
        assertEquals((byte) 0x0F, result[3]);
    }

    @Test
    public void IPField() throws Exception {
        byte[] result = this.message.IPField(InetAddress.getByName("127.0.0.1"));
        assertEquals(8, result.length);
        assertEquals(0x0, result[0]);
        assertEquals(0x1, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x4, result[3]);
        assertEquals(0x7F, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x1, result[7]);
    }

    @Test
    public void portField() throws Exception {
        byte[] result = this.message.portField(5000);
        assertEquals(6, result.length);
        assertEquals(((byte) 0x0), result[0]);
        assertEquals(((byte) 0x2), result[1]);
        assertEquals(((byte) 0x0), result[2]);
        assertEquals(((byte) 0x2), result[3]);
        assertEquals(((byte) 0x13), result[4]);
        assertEquals(((byte) 0x88), result[5]);
    }

    @Test
    public void nameField() throws Exception {
        byte[] result = this.message.nameField("FOO");
        assertEquals(7, result.length);
        assertEquals(0x0, result[0]);
        assertEquals(0x4, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x3, result[3]);
        assertEquals((byte) 0x46, result[4]);
        assertEquals((byte) 0x4f, result[5]);
        assertEquals((byte) 0x4f, result[6]);
    }

    @Test
    public void textField() throws Exception {
        byte[] result = this.message.textField("BAR");
        assertEquals(7, result.length);
        assertEquals(0x0, result[0]);
        assertEquals(0x5, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x3, result[3]);
        assertEquals((byte) 0x42, result[4]);
        assertEquals((byte) 0x41, result[5]);
        assertEquals((byte) 0x52, result[6]);
    }
}