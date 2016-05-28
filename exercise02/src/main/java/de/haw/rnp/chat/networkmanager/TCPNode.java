package de.haw.rnp.chat.networkmanager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPNode extends Node {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader in;

    public TCPNode(int port, InetAddress hostName) {
        super(port, hostName);
    }

    public BufferedReader getIn() {
        return in;
    }

    public void startClientNode() {
        try {
            this.clientSocket = new Socket(this.hostName, this.port);
            this.out = this.clientSocket.getOutputStream();
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + this.hostName.getHostAddress());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + this.hostName.getHostAddress());
            e.printStackTrace();
        }
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

    public void startServerNode() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Port is not available");
            e.printStackTrace();
        }

        try {
            this.clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Client Accept failed");
            e.printStackTrace();
        }

        System.out.println("Client connected to Server");

        try {
            this.out = this.clientSocket.getOutputStream();
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Trouble reading i/o");
            e.printStackTrace();
        }
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
}
