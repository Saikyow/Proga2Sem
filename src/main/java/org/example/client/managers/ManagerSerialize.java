package org.example.client.managers;

import org.example.packet.request.CommandPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ManagerSerialize {
    public static byte[] serialize(CommandPacket packet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(packet);
        oos.flush();
        return baos.toByteArray();
    }
}
