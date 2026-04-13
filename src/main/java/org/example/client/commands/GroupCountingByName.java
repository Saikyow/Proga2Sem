package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;


public class GroupCountingByName implements Command {


    public void executeCommand(String[] args, SocketChannel serverChannel) throws  IOException, ClassNotFoundException {
        if (checkArg(args)) {

            CommandPacket commandPacket = new CommandPacket("group_counting_by_name", args, null);


            writeModule.writePacketForServer(serverChannel, commandPacket);

            ResponsePacket response = readModule.readResponseForClient(serverChannel);

            if (response != null) {
                managerInputOutput.writeLineIO("Сервер: " + response.getMessage() + "\n");
            }


        }
    }


    private boolean checkArg(String[] args) {
        if (args == null || args.length == 0) {
            return true;
        }
        managerInputOutput.writeLineIO("Ошибка! Команда group_counting_by_name не принимает аргументы. \n");
        return false;
    }

    @Override
    public String toString() {
        return "group_counting_by_name - группирует элементы коллекции по значению поля name, выводит количество элементов в каждой группе";
    }
}