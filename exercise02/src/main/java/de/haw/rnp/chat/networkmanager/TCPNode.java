package de.haw.rnp.chat.networkmanager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPNode extends Node {
    private Socket clientSocket;
    private ServerSocket serverSocket;

    public TCPNode(int port, InetAddress hostName) {
        super(port, hostName);
    }
}
