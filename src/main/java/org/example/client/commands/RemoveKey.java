package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;
public class RemoveKey implements Command {

    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {

        if (checkArgs(args)) {
            CommandPacket commandPacket = new CommandPacket("remove_key", args, null);


            writeModule.writePacketForServer(serverChannel, commandPacket);

            ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);

            if (responsePacket != null) {
                managerInputOutput.writeLineIO("Сервер: "  + responsePacket.getMessage());
            }




        }
    }

    public boolean checkArgs(String[] args) {
        if (args == null || args.length != 1) {
            return false;
        }

        try {
            Long.parseLong(args[0]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return "remove_key - удаляет элемент из коллекции по его ключу";
    }
}