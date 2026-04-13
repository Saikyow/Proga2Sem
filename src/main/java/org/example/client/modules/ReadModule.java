package org.example.client.modules;

import org.example.packet.request.ResponsePacket;
import org.example.server.managers.ManagerDeserialize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadModule {

    public ResponsePacket readResponseForClient(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

        while (lengthBuffer.hasRemaining()) {
            int read = channel.read(lengthBuffer);

            if (read == -1) {
                throw new IOException("Сервер закрыл соединение");
            }

            if (read == 0) {
                Thread.yield();
            }
        }

        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        if (length <= 0 || length > 10_000_000) {
            throw new IOException("Слишком большой ответ: " + length + " байт");
        }

        ByteBuffer dataBuffer = ByteBuffer.allocate(length);

        while (dataBuffer.hasRemaining()) {
            int read = channel.read(dataBuffer);

            if (read == -1) {
                throw new IOException("Сервер закрыл соединение");
            }

            if (read == 0) {
                Thread.yield();
            }
        }

        return (ResponsePacket) ManagerDeserialize.deserialize(dataBuffer.array());
    }
}