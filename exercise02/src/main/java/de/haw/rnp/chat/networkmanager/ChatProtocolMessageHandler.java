package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.Controller;
import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ClientStartTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerReadTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Handles all protocol messages that are forwarded between the peers.
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class ChatProtocolMessageHandler implements MessageHandler {

    private IControllerService controller;
    private TCPNodeFactory factory;
    private ExecutorService executor;

    public ChatProtocolMessageHandler(Controller controller) {
        this.controller = controller;
        this.factory = new TCPNodeFactory();
        this.executor = Executors.newCachedThreadPool();
    }

    public byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    public byte[] createCommonHeader(byte messageType, byte[] senderIP, byte[] port, byte[] fieldCount) {
        byte[] result = new byte[12];
        byte[] firstRow = new byte[]{0x01, messageType, 0x00, 0x00};

        System.arraycopy(firstRow, 0, result, 0, firstRow.length);
        System.arraycopy(senderIP, 0, result, 4, senderIP.length);
        System.arraycopy(port, 2, result, 8, 2);
        System.arraycopy(fieldCount, 2, result, 10, 2);

        return result;
    }

    public byte[] IPField(InetAddress hostName) {
        byte[] result = new byte[8];
        result[1] = 0x1;
        result[3] = 0x4;
        System.arraycopy(hostName.getAddress(), 0, result, 4, 4);
        return result;
    }

    public byte[] portField(int port) {
        byte[] result = new byte[6];
        result[1] = 0x2;
        result[3] = 0x2;
        byte[] portByte = this.intToByteArray(port);
        result[4] = portByte[2];
        result[5] = portByte[3];
        return result;
    }

    public byte[] nameField(String name) {
        byte[] nameByte = name.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[4 + nameByte.length];
        result[1] = 0x4;
        result[3] = (byte) nameByte.length;
        System.arraycopy(nameByte, 0, result, 4, nameByte.length);
        return result;
    }

    public byte[] textField(String text) {
        byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[4 + textByte.length];
        result[1] = 0x5;
        result[3] = (byte) textByte.length;
        System.arraycopy(textByte, 0, result, 4, textByte.length);
        return result;
    }

    public byte[] createLoginMessage(InetAddress senderHostName, int senderPort, String loginUserName, InetAddress loginUserHostName, int loginUserPort) {
        byte[] commonHeader = this.createCommonHeader((byte) 0x01, senderHostName.getAddress(), this.intToByteArray(senderPort), this.intToByteArray(3));
        byte[] ipField = this.IPField(loginUserHostName);
        byte[] portField = this.portField(loginUserPort);
        byte[] nameByte = this.nameField(loginUserName);
        byte[] result = new byte[(26 + nameByte.length)];
        System.arraycopy(commonHeader, 0, result, 0, 12);
        System.arraycopy(ipField, 0, result, 12, 8);
        System.arraycopy(portField, 0, result, 20, 6);
        System.arraycopy(nameByte, 0, result, 26, nameByte.length);
        return result;
    }

    public TCPNodeFactory getFactory() {
        return factory;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void processMessage(byte[] protocolMessage) {
        byte version = protocolMessage[0];
        byte messageType = protocolMessage[1];

        /*if (messageType == 0x01) {
            String name = "";
            int port = 0;
            String hostname = "";
            InetAddress hostName = new Inet4Address(hostname);
            this.controller.setLoggedInUser(this.login(name, hostName, port));
        }*/
    }

    @Override
    public User login(Node clientNode, InetAddress senderHostName, int senderPort, String loginName, InetAddress loginHostName, int loginPort) {
        User user = new User(loginName, clientNode); //clientnode: my connection to the other peer
        Node serverNode = this.factory.createNode(senderHostName, senderPort); //me
        user.setServerNode(serverNode);
        ServerStartTask task = new ServerStartTask(serverNode);
        Future<Boolean> result = this.executor.submit(task);
        byte[] loginMessage = this.createLoginMessage(senderHostName, senderPort, loginName, loginHostName, loginPort);
        byte[] loginMessageAck = this.createLoginMessage(clientNode.getHostName(), clientNode.getPort(), loginName, loginHostName, loginPort);
        try {
            user.getClientNode().getOut().write(loginMessage); //TODO: Wait for ack (login propagate) to successfully login?
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void receiveMessage(Message message) {
        this.controller.addMessageToQueue(message);
    }

    @Override
    public void sendName(User user, List recipient) {

    }

    @Override
    public void changeName(String name, InetAddress hostName, int port) {

    }

    @Override
    public Node initialConnect(InetAddress hostName, int port) {
        Node clientNode = null;
        try {
            if (hostName.isReachable(2000)) {
                clientNode = this.factory.createNode(hostName, port);
                ClientStartTask task = new ClientStartTask(clientNode);
                Future<Boolean> result = this.executor.submit(task);
                if (result.get()) {
                    return clientNode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return clientNode;
    }
}
