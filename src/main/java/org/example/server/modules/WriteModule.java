package org.example.server.modules;

import org.example.packet.request.ResponsePacket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteModule {

    public void writeResponseForClient(Socket socket, ResponsePacket response) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(response);
        out.flush();
    }
}