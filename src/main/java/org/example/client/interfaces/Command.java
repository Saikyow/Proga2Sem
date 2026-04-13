package org.example.client.interfaces;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface Command extends Cloneable {

    String toString();

    void executeCommand(String[] args, SocketChannel serverChannel)
            throws IOException, ClassNotFoundException;
}