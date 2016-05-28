package de.haw.rnp.chat.networkmanager;

import java.net.InetAddress;

public abstract class NodeFactory {
    public abstract Node createNode(InetAddress hostName, int port);
}
