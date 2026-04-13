package org.example.server.modules;

import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadModule {

    public CommandPacket readCommandFromClient(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Object obj = in.readObject();
        return (CommandPacket) obj;
    }
}