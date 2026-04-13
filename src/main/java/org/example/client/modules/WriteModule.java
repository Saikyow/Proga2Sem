package org.example.client.modules;

import org.example.packet.request.CommandPacket;
import org.example.server.managers.ManagerSerialize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteModule {

    public void writePacketForServer(SocketChannel channel, CommandPacket packet) throws IOException {
        byte[] data = ManagerSerialize.serialize(packet);

        ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();

        while (buffer.hasRemaining()) {
            int written = channel.write(buffer);

            if (written == -1) {
                throw new IOException("Соединение закрыто во время записи");
            }

            if (written == 0) {
                Thread.yield();
            }
        }
    }
}