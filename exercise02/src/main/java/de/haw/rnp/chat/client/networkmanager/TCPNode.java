package de.haw.rnp.chat.client.networkmanager;

import de.haw.rnp.chat.client.controller.IControllerService;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Actual Product produced by the actual Factory.
 */
public class TCPNode extends Node {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BlockingQueue<Socket> incomingSockets;
    private IncomingMessageHandler incomingMessageHandler;
    private HashMap<SocketAddress, Instant> timeMap = new HashMap<>();

    public TCPNode(int port, InetAddress hostName) {
        super(port, hostName);
        this.incomingSockets = new LinkedBlockingQueue<>();
    }

    public boolean startClientNode() {
        try {
            this.clientSocket = new Socket(this.hostName, this.port);
            this.out = this.clientSocket.getOutputStream();
            System.out.println("Client started");
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + this.hostName.getHostAddress());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + this.hostName.getHostAddress());
            e.printStackTrace();
        }
        return false;
    }

    public boolean stopClientNode() {
        try {
            this.clientSocket.close();
            this.out.close();
            System.out.println("Client closed");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean startServerNode(IControllerService controller) {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.hostName = this.serverSocket.getInetAddress();
            this.incomingMessageHandler = new IncomingChatProtocolMessageHandler(controller);
            System.out.println("Server started at " + this.hostName.getHostAddress() + ":" + this.port);
            return true;
        } catch (IOException e) {
            System.err.println("Port is not available: " + this.port);
            e.printStackTrace();
        }
        return false;
    }

    public boolean stopServerNode() {
        try {
            this.serverSocket.close();
            this.out.close();
            System.out.println("Server closed");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void readServerInput() throws InterruptedException, IOException {
        Socket s = this.incomingSockets.take();
        byte[] data = IOUtils.toByteArray(s.getInputStream());
        if (data.length > 0) {
            this.incomingMessageHandler.processMessage(data);
            int align = 1;
            for (byte b : data) {
                System.out.format("0x%x ", b);
                if ((align % 4) == 0 && align > 1) {
                    System.out.print("\n");
                }
                align++;
            }
        }
    }

    public void awaitConnections() {
        try {
            Socket incomingSocket = serverSocket.accept();
            this.incomingSockets.offer(incomingSocket);
        } catch (IOException e) {
            System.err.println("Client Accept failed");
        }

        System.out.println("Client connected to Server " + this.hostName.getHostAddress() + ":" + this.port);
    }

    @Override
    public BlockingQueue<Socket> getIncomingSockets() {
        return this.incomingSockets;
    }
}