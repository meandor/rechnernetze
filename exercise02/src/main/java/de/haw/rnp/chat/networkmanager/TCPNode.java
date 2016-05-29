package de.haw.rnp.chat.networkmanager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPNode extends Node {
    private Socket clientSocket;
    private ServerSocket serverSocket;

    public TCPNode(int port, InetAddress hostName) {
        super(port, hostName);
    }

    public boolean startClientNode() {
        try {
            this.clientSocket = new Socket(this.hostName, this.port);
            this.out = this.clientSocket.getOutputStream();
            this.in = this.clientSocket.getInputStream();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean startServerNode() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Port is not available: " + this.port);
            e.printStackTrace();
        }

        try {
            this.clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Client Accept failed");
            e.printStackTrace();
        }

        System.out.println("Client connected to Server " + this.hostName.getHostAddress() + ":" + this.port);

        try {
            this.out = this.clientSocket.getOutputStream();
            this.in = this.clientSocket.getInputStream();
            return true;
        } catch (IOException e) {
            System.err.println("Trouble reading i/o");
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
        int count;
        byte[] data = new byte[4];
        try {
            while ((count = this.in.read(data)) != 0) {
                for (byte b : data) {
                    System.out.format("0x%x ", b);
                }
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
