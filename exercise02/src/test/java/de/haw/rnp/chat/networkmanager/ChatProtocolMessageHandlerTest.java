package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatProtocolMessageHandlerTest {

    private ChatProtocolMessageHandler messageHandler;

    @Before
    public void setUp() throws Exception {
        this.messageHandler = new ChatProtocolMessageHandler(null);
    }

    @Test
    public void processMessage() throws Exception {
        /*assertEquals("1", this.messageHandler.processMessage(this.message)[0]);
        assertEquals("TextMessage", this.messageHandler.processMessage(this.message)[1]);
        assertEquals("10.0.0.1", this.messageHandler.processMessage(this.message)[2]);
        assertEquals("10", this.messageHandler.processMessage(this.message)[2]);
        assertEquals("2", this.messageHandler.processMessage(this.message)[3]);
        assertEquals("Text", this.messageHandler.processMessage(this.message)[4]);
        assertEquals("A", this.messageHandler.processMessage(this.message)[5]);*/
    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void sendMessage() throws Exception {

    }

    @Test
    public void receiveMessage() throws Exception {

    }

    @Test
    public void sendName() throws Exception {

    }

    @Test
    public void changeName() throws Exception {

    }

    @Test
    public void initialConnect() throws Exception {
        Node server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 1337);
        ServerStartTask task = new ServerStartTask(server);
        this.messageHandler.getExecutor().execute(task);
        User user = this.messageHandler.initialConnect(server.getHostName(), server.getPort());
        assertTrue(1337 == server.getPort());
        assertEquals("", user.getName());
        //TODO: Test message sending!
    }

    @Test
    public void intToByteArray() throws Exception {
        byte[] result = this.messageHandler.intToByteArray(1);
        assertEquals((byte) 0x00, result[0]);
        assertEquals((byte) 0x00, result[1]);
        assertEquals((byte) 0x00, result[2]);
        assertEquals((byte) 0x01, result[3]);
        result = this.messageHandler.intToByteArray(15);
        assertEquals((byte) 0x00, result[0]);
        assertEquals((byte) 0x00, result[1]);
        assertEquals((byte) 0x00, result[2]);
        assertEquals((byte) 0x0F, result[3]);
    }

    @Test
    public void createCommonHeader() throws Exception {
        byte[] senderIP = InetAddress.getByName("10.0.0.3").getAddress();
        byte[] port = this.messageHandler.intToByteArray(11);
        byte[] length = this.messageHandler.intToByteArray(13);
        byte[] result = this.messageHandler.createCommonHeader((byte) 0x01, senderIP, port, length);
        assertEquals(0x1, result[0]);
        assertEquals(0x1, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x3, result[7]);
        assertEquals(0x0, result[8]);
        assertEquals(0xB, result[9]);
        assertEquals(0x0, result[10]);
        assertEquals(0xD, result[11]);
    }

    @Test
    public void IPField() throws Exception {
        byte[] result = this.messageHandler.IPField(InetAddress.getByName("127.0.0.1"));
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
        byte[] result = this.messageHandler.portField(5000);
        assertEquals(0x0, result[0]);
        assertEquals(0x2, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x2, result[3]);
        assertEquals(0x13, result[4]);
        assertEquals((byte) 0x88, result[5]);
    }

    @Test
    public void nameField() throws Exception {
        byte[] result = this.messageHandler.nameField("FOO");
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
        byte[] result = this.messageHandler.textField("BAR");
        assertEquals(0x0, result[0]);
        assertEquals(0x5, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x3, result[3]);
        assertEquals((byte) 0x42, result[4]);
        assertEquals((byte) 0x41, result[5]);
        assertEquals((byte) 0x52, result[6]);
    }

    @Test
    public void createLoginMessage() throws Exception {
        byte[] result = this.messageHandler.createLoginMessage(InetAddress.getByName("10.0.0.1"), 20, "FOO", InetAddress.getByName("127.0.0.1"), 13);
        assertEquals(0x1, result[0]);
        assertEquals(0x1, result[1]);
        assertEquals(0x0, result[2]);
        assertEquals(0x0, result[3]);
        assertEquals(0xA, result[4]);
        assertEquals(0x0, result[5]);
        assertEquals(0x0, result[6]);
        assertEquals(0x1, result[7]);
        assertEquals(0x0, result[8]);
        assertEquals(0x14, result[9]);
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
        assertEquals(0x0, result[24]);
        assertEquals((byte) 0xD, result[25]);
        assertEquals(0x0, result[26]);
        assertEquals(0x4, result[27]);
        assertEquals(0x0, result[28]);
        assertEquals(0x3, result[29]);
        assertEquals((byte) 0x46, result[30]);
        assertEquals((byte) 0x4f, result[31]);
        assertEquals((byte) 0x4f, result[32]);
    }
}