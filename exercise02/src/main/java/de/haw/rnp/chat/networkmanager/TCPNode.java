package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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

    public void readServerInput() {
        HashMap<InetAddress, Instant> timeMap = new HashMap<>();
        for (Socket s : this.getIncomingSockets()) {
            try {
                if (s.getInputStream().available() != 0) {
                    byte[] data = new byte[s.getInputStream().available()];
                    int count = s.getInputStream().read(data);
                    this.incomingMessageHandler.processMessage(data);
                    int align = 1;
                    for (byte b : data) {
                        System.out.format("0x%x ", b);
                        if ((align % 4) == 0 && align > 1) {
                            System.out.print("\n");
                        }
                        align++;
                    }
                } else {
                    //TODO: If multiple clients from the same ip, problem!
                    if (timeMap.containsKey(s.getInetAddress())) {
                        Instant stamp = timeMap.get(s.getInetAddress());
                        if (Instant.now().isAfter(stamp.plusSeconds(10))) {
                            s.close();
                        }
                    } else {
                        timeMap.put(s.getInetAddress(), Instant.now());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
