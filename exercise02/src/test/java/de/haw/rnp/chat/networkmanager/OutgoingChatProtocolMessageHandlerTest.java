package de.haw.rnp.chat.networkmanager;

import com.sun.deploy.util.SessionState;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ClientCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerCloseTask;
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
        // Start test server
        Node server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 5050);
        ServerStartTask serverStartTask = new ServerStartTask(server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(serverStartTask);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask serverAwaitConnectionsTask = new ServerAwaitConnectionsTask(server);
            this.messageHandler.getExecutor().execute(serverAwaitConnectionsTask);
            User u = this.messageHandler.login(InetAddress.getByName("127.0.0.1"), 5050, "FOO", InetAddress.getByName("10.0.0.1"), 15533);
            assertNotNull(u);
            assertEquals("FOO", u.getName());
            assertEquals("10.0.0.1", u.getHostName().getHostAddress());
            assertEquals(15533, u.getPort());
            //Stop Server
            //ServerCloseTask serverCloseTask = new ServerCloseTask(server);
            //this.messageHandler.getExecutor().execute(serverCloseTask);
        } else {
            //Stop Server
            ServerCloseTask serverCloseTask = new ServerCloseTask(server);
            this.messageHandler.getExecutor().execute(serverCloseTask);
            assert false;
        }
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
        Node server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 5050);
        ServerStartTask task = new ServerStartTask(server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(task);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(server);
            this.messageHandler.getExecutor().execute(task2);
            Node node = this.messageHandler.initialConnect(server.getHostName(), server.getPort());
            assertNotNull(node);
            assertTrue(5050 == server.getPort());
            assertEquals(InetAddress.getByName("0.0.0.0"), node.getHostName());
            //Stop Client
            ClientCloseTask clientCloseTask = new ClientCloseTask(node);
            this.messageHandler.getExecutor().execute(clientCloseTask);
            //Stop Server
            task2.stop();;
            ServerCloseTask serverCloseTask = new ServerCloseTask(server);
            this.messageHandler.getExecutor().execute(serverCloseTask);
        }
    }
}