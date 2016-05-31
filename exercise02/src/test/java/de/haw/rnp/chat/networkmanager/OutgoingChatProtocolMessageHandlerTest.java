package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OutgoingChatProtocolMessageHandlerTest {

    private OutgoingMessageHandler messageHandler;

    @Before
    public void setUp() throws Exception {
        this.messageHandler = new OutgoingChatProtocolMessageHandler(null);
    }

    @Test
    public void login() throws Exception {
        /*Node server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 5050);
        ServerStartTask task = new ServerStartTask(server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(task);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(server);
            this.messageHandler.getExecutor().execute(task2);
            Node node = this.messageHandler.initialConnect(server.getHostName(), server.getPort());
            while (server.getIncomingSockets().size() == 0) {

            }
            System.out.println(String.valueOf(server.getIncomingSockets().size()));
            if (server.getIncomingSockets().size() == 1) {
                System.out.println("Start reading");
                ServerReadTask task3 = new ServerReadTask(server);
                this.messageHandler.getExecutor().execute(task3);
            }
            User user = this.messageHandler.login(node, "FOO", InetAddress.getByName("10.0.0.1"), 27515);
            assertNotNull(user);
            assertEquals("FOO", user.getName());
        } else {
            assert false;
        }*/
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
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(task);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(server);
            this.messageHandler.getExecutor().execute(task2);
            Node node = this.messageHandler.initialConnect(server.getHostName(), server.getPort());
            assertNotNull(node);
            assertTrue(1337 == server.getPort());
            assertEquals(InetAddress.getByName("0.0.0.0"), node.getHostName());
        }
    }
}