package de.haw.rnp.chat.networkmanager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatProtocolMessageHandlerTest {

    private ChatProtocolMessageHandler messageHandler;
    private byte[] message;

    @Before
    public void setUp() throws Exception {
        this.messageHandler = new ChatProtocolMessageHandler(null);
        this.message = this.createTextMessage();
    }

    private byte[] createTextMessage() {
        return new byte[]{0x01, 0x03, 0x00, 0x00, 0x0A, 0x00, 0x00, 0x01, 0x00, 0x0A, 0x00, 0x02, 0x05, 0x41};
    }

    private byte[] createLoginMessage() {
        return new byte[]{
                0x01, 0x01, 0x00, 0x00, // common header
                0x0A, 0x00, 0x00, 0x01,
                0x00, 0x0A, 0x00, 0x02,

                0x00, 0x01, 0x00, 0x04, // field 1
                0x0A, 0x00, 0x00, 0x02,
                0x00, 0x02, 0x00, 0x02, //field 2
                0x00, 0x10};
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

    }

}