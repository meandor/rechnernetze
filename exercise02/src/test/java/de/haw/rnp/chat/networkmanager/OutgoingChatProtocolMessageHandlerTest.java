package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ClientCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerReadTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OutgoingChatProtocolMessageHandlerTest {

    private OutgoingMessageHandler messageHandler;
    private Node server;

    @Before
    public void setUp() throws Exception {
        this.messageHandler = new OutgoingChatProtocolMessageHandler(null);
    }

    @After
    public void tearDown() throws Exception {
        this.messageHandler.getExecutor().shutdown();
    }

    @Test
    public void login() throws Exception {
        // Start new test server
        this.server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 8080);
        ServerStartTask serverStartTask = new ServerStartTask(this.server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(serverStartTask);
        if (serverStarted.get()) {
            ServerReadTask read = new ServerReadTask(this.server);
            this.messageHandler.getExecutor().execute(read);
            ServerAwaitConnectionsTask serverAwaitConnectionsTask = new ServerAwaitConnectionsTask(this.server);
            this.messageHandler.getExecutor().execute(serverAwaitConnectionsTask);
            User u = this.messageHandler.login(this.server.getHostName(), this.server.getPort(), "FOO", InetAddress.getByName("10.0.0.1"), 15533);
            assertNotNull(u);
            assertEquals("FOO", u.getName());
            assertEquals("10.0.0.1", u.getHostName().getHostAddress());
            assertEquals(15533, u.getPort());
        } else {
            assert false;
        }
    }

    @Test
    public void logout() throws Exception {
        // Start new test server
        this.server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 27015);
        ServerStartTask serverStartTask = new ServerStartTask(this.server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(serverStartTask);
        if (serverStarted.get()) {
            ServerReadTask read = new ServerReadTask(this.server);
            this.messageHandler.getExecutor().execute(read);
            ServerAwaitConnectionsTask serverAwaitConnectionsTask = new ServerAwaitConnectionsTask(this.server);
            this.messageHandler.getExecutor().execute(serverAwaitConnectionsTask);
            this.messageHandler.logout(this.server.getHostName(), this.server.getPort(), InetAddress.getByName("10.0.0.1"), 15533);
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void sendMessage() throws Exception {

    }

    @Test
    public void sendName() throws Exception {
        // Start new test server
        this.server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 27115);
        ServerStartTask serverStartTask = new ServerStartTask(this.server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(serverStartTask);
        if (serverStarted.get()) {
            ServerReadTask read = new ServerReadTask(this.server);
            this.messageHandler.getExecutor().execute(read);
            ServerAwaitConnectionsTask serverAwaitConnectionsTask = new ServerAwaitConnectionsTask(this.server);
            this.messageHandler.getExecutor().execute(serverAwaitConnectionsTask);
            this.messageHandler.sendName(this.server.getHostName(), this.server.getPort(), "FOO", InetAddress.getByName("10.0.0.1"), 15533);
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void initialConnect() throws Exception {
        this.server = this.messageHandler.getFactory().createNode(InetAddress.getByName("127.0.0.1"), 5050);
        ServerStartTask task = new ServerStartTask(this.server);
        Future<Boolean> serverStarted = this.messageHandler.getExecutor().submit(task);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(server);
            this.messageHandler.getExecutor().execute(task2);
            Node node = this.messageHandler.initialConnect(this.server.getHostName(), this.server.getPort());
            assertNotNull(node);
            assertTrue(5050 == this.server.getPort());
            assertEquals(InetAddress.getByName("0.0.0.0"), node.getHostName());
            //Stop Client
            ClientCloseTask clientCloseTask = new ClientCloseTask(node);
            this.messageHandler.getExecutor().submit(clientCloseTask);
            assert true;
        } else {
            assert false;
        }
    }
}