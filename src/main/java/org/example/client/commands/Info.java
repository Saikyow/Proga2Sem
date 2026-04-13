

package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.modules.ReadModule;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;

import static org.example.client.Client.*;

public class Info implements Command {

    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {

        if (checkArgs(args)) {
            CommandPacket commandPacket = new CommandPacket("info", args, null);
                writeModule.writePacketForServer(serverChannel, commandPacket);

                ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);

                if (responsePacket != null){
                    Map<String, Object> info = (Map<String, Object>) responsePacket.getData();
                    managerInputOutput.writeLineIO("Количество элементов " + info.get("size") + "\n");
                    managerInputOutput.writeLineIO("Время инициализации " + info.get("initTime") + "\n");
                    managerInputOutput.writeLineIO("Тип данных Person \n");
                }else {
                    managerInputOutput.writeLineIO("Сервер " + responsePacket.getMessage() + "\n");
                }



        }


    }

    public boolean checkArgs(String[] args) {
        return args.length == 0;
    }

    @Override
    public String toString() {
        return "info - выводит информацию о коллекции";
    }
}



