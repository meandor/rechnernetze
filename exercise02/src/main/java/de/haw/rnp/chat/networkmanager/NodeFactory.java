package de.haw.rnp.chat.networkmanager;

import java.net.InetAddress;

/**
 * Factory Pattern abstract Factory for creating Nodes.
 */
public abstract class NodeFactory {
    public abstract Node createNode(InetAddress hostName, int port);
}
