package org.example.client;

import org.example.client.managers.ManagerInputOutput;
import org.example.client.managers.ManagerParserClient;
import org.example.client.modules.ReadModule;
import org.example.client.modules.WriteModule;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;

public class Client {
    public static ManagerInputOutput managerInputOutput = ManagerInputOutput.getInstance();
    public static ManagerParserClient managerParserClient = new ManagerParserClient();
    public static SocketChannel server = null;
    public static ReadModule readModule = new ReadModule();
    public static WriteModule writeModule = new WriteModule();

    public static void main(String[] args) throws IOException {
        int port = 6666;

        try {
            managerInputOutput.setCommands(managerParserClient.getCommandNames());

            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                managerInputOutput.writeLineIO("Порт по умолчанию: 6666\n");
            }

            reconnectLoop(port);

            while (true) {
                String input = managerInputOutput.readLineIO("Введите команду : ");

                try {
                    managerParserClient.parserCommand(input);
                } catch (IOException e) {
                    managerInputOutput.writeLineIO(
                            "Соединение с сервером потеряно: " + e.getMessage() + "\n"
                    );
                    reconnectLoop(port);
                } catch (ClassNotFoundException e) {
                    managerInputOutput.writeLineIO("Ошибка десериализации ответа от сервера\n");
                }
            }

        } catch (NoSuchElementException e) {
            managerInputOutput.writeLineIO("Завершение работы\n");
        } catch (Exception e) {
            managerInputOutput.writeLineIO("Ошибка во время работы программы\n");
            e.printStackTrace();
        } finally {
            closeConnection();
            managerInputOutput.closeIO();
        }
    }

    private static boolean connect(int port) {
        try {
            managerInputOutput.writeLineIO("Подключение к серверу...\n");

            server = SocketChannel.open();
            server.configureBlocking(false);
            server.connect(new InetSocketAddress("localhost", port));

            while (!server.finishConnect()) {
                Thread.yield();
            }

            managerInputOutput.writeLineIO("Вы подключились к серверу\n");
            return true;

        } catch (IOException e) {
            managerInputOutput.writeLineIO("Сервер недоступен\n");
            closeConnection();
            return false;
        }
    }

    private static void reconnectLoop(int port) {
        closeConnection();

        while (true) {
            try {
                managerInputOutput.writeLineIO("Подключение к серверу\n");

                server = SocketChannel.open();
                server.configureBlocking(false);
                server.connect(new InetSocketAddress("localhost", port));

                while (!server.finishConnect()){
                    Thread.yield();
                }
                managerInputOutput.writeLineIO("Вы подключились к сервере \n");
                return;


            }catch (IOException e) {
                managerInputOutput.writeLineIO("Сервер недоступен. Нажмите Enter для повторной попытки или введите exit\n");
                String s = managerInputOutput.readLineIO();
                if (s != null && s.trim().equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
            }
        }
    }

    private static void closeConnection() {
        try {
            if (server != null && server.isOpen()) {
                server.close();
            }
        } catch (IOException ignored) {
        }
    }
}