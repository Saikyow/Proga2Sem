package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.packet.request.CommandPacket;
import org.example.packet.request.ResponsePacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;

public class Save implements Command{

    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {
        CommandPacket commandPacket = new CommandPacket("save", args, null);


            writeModule.writePacketForServer(serverChannel, commandPacket);

            ResponsePacket response = readModule.readResponseForClient(serverChannel);

            if (response != null) {
                managerInputOutput.writeLineIO("Сервер: "  + response.getMessage() + "\n");
            }

    }

    public String toString(){
        return "save - сохраняет коллекцию";
    }
    public boolean checkArg(String[] args) {
        if (args == null || args.length == 0) {
            return true;
        } else {
            managerInputOutput.writeLineIO("Команда show не принимает аргументы \n");
            return false;
        }
    }




}