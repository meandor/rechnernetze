package de.haw.rnp.chat.networkmanager;

import java.net.InetAddress;

/**
 * Actual Factory from the Factory pattern.
 */
public class TCPNodeFactory extends NodeFactory {

    @Override
    public Node createNode(InetAddress hostName, int port) {
        return new TCPNode(port, hostName);
    }
}
