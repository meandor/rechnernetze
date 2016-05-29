package de.haw.rnp.chat.networkmanager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPNode extends Node {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BlockingQueue<Socket> incomingSockets;

    public TCPNode(int port, InetAddress hostName) {
        super(port, hostName);
        this.incomingSockets = new LinkedBlockingQueue<>();
    }

    public boolean startClientNode() {
        try {
            this.clientSocket = new Socket(this.hostName, this.port);
            this.out = this.clientSocket.getOutputStream();
            this.in = this.clientSocket.getInputStream();
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

    public void stopClientNode() {
        try {
            this.clientSocket.close();
            this.out.close();
            this.in.close();
            System.out.println("Client closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean startServerNode() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.hostName = this.serverSocket.getInetAddress();
            System.out.println("Server started at " + this.hostName.getHostAddress() + ":" + this.port);
            return true;
        } catch (IOException e) {
            System.err.println("Port is not available: " + this.port);
            e.printStackTrace();
        }
        return false;
    }

    public void stopServerNode() {
        try {
            this.out.close();
            this.in.close();
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readServerInput() {
        while (true) {
            for (Socket s : this.getIncomingSockets()) {
                try {
                    if (s.getInputStream().available() != 0) {
                        byte[] data = new byte[s.getInputStream().available()];
                        int count = s.getInputStream().read(data);
                        int align = 1;
                        for (byte b : data) {
                            System.out.format("0x%x ", b);
                            if ((align % 4) == 0 && align > 1) {
                                System.out.print("\n");
                            }
                            align++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void awaitConnections() {
        while (true) {
            try {
                Socket incomingSocket = serverSocket.accept();
                this.incomingSockets.offer(incomingSocket);
            } catch (IOException e) {
                System.err.println("Client Accept failed");
                e.printStackTrace();
            }

            System.out.println("Client connected to Server " + this.hostName.getHostAddress() + ":" + this.port);
        }
    }

    @Override
    public BlockingQueue<Socket> getIncomingSockets() {
        return this.incomingSockets;
    }
}
