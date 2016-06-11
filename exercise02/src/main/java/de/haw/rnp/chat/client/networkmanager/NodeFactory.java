package de.haw.rnp.chat.client.networkmanager;

import java.net.InetAddress;

/**
 * Factory Pattern abstract Factory for creating Nodes.
 */
public abstract class NodeFactory {
    public abstract Node createNode(InetAddress hostName, int port);
}
