package org.example.client.commands;

import org.example.client.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;

public class Clear implements Command {
    public void executeCommand(String[] args, SocketChannel serverChannel) throws  IOException, ClassNotFoundException {
        if (checkArgs(args)) {
            CommandPacket packet = new CommandPacket("clear", args, null);




                writeModule.writePacketForServer(serverChannel, packet);

                ResponsePacket response = readModule.readResponseForClient(serverChannel);

                if (response != null){
                    managerInputOutput.writeLineIO("Сервер: " + response.getMessage() + "\n");
                }

        }
    }









    public boolean checkArgs(String[] args) {
        return args.length == 0;
    }

    public String toString(){
        return "clear - очищает коллекцию";
    }
}