package org.example.server;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.logger.ServerLogger;
import org.example.server.managers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static CollectionManager collectionManager;
    public static ManagerParserServer managerParserServer;

    public static void main(String[] args) {
        String filePath;
        int port = 6666;

        try {
            ServerLogger.info("Запуск сервера");

            if (args == null || args.length == 0) {
                ServerLogger.error("Не передан путь к csv-файлу");
                return;
            }

            filePath = args[0];

            if (args.length > 1) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    ServerLogger.error("Некорректный порт, используется 6666");
                }
            }

            collectionManager = new CollectionManager();

            List<String[]> data = csvParserManager.readCSV(filePath);
            collectionManager.addAllCollection(data);

            managerParserServer = new ManagerParserServer(collectionManager, filePath);

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                ServerLogger.info("Сервер запущен на порту " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    ServerLogger.info("Подключился клиент: " + clientSocket.getInetAddress());

                    toClient(clientSocket);
                }
            }catch (BindException e){
                ServerLogger.error("Порт " + port + " уже занят. Сервер не может быть запущен.");
            }

        } catch (Exception e) {
            ServerLogger.error("Ошибка запуска сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void toClient(Socket clientSocket) {
        try (
                Socket socket = clientSocket;
                java.io.DataInputStream in = new java.io.DataInputStream(socket.getInputStream());
                java.io.DataOutputStream out = new java.io.DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                int length = in.readInt();
                if (length <= 0 || length > 10_000_000) {
                    throw new IOException("Некорректный размер запроса: " + length);
                }

                byte[] requestBytes = new byte[length];
                in.readFully(requestBytes);

                CommandPacket packet = (CommandPacket) ManagerDeserialize.deserialize(requestBytes);
                ResponsePacket response = managerParserServer.execute(packet);

                byte[] responseBytes = ManagerSerialize.serialize(response);

                out.writeInt(responseBytes.length);
                out.write(responseBytes);
                out.flush();
            }

        } catch (Exception e) {
            ServerLogger.error("Клиент отключился или произошла ошибка: " + e.getMessage());
        }
    }
}