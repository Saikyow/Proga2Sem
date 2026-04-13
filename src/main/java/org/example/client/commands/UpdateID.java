package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.client.managers.ManagerCreatorPerson;
import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;

public class UpdateID implements Command {

    private final ManagerCreatorPerson managerCreatorPerson = new ManagerCreatorPerson();


    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {

        if (checkArgs(args)) {

            Person person = managerCreatorPerson.createPerson();



            CommandPacket commandPacket = new CommandPacket("update_id", args, person);


            writeModule.writePacketForServer(serverChannel, commandPacket);

            ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);
            if (responsePacket != null){
                managerInputOutput.writeLineIO("Сервер: " + responsePacket.getMessage());
            }


        }
    }

    public boolean checkArgs(String[] args) {
        if (args.length == 1) {
            try {
                Long.parseLong(args[0]);
                return true;
            }catch (NumberFormatException e){
                managerInputOutput.writeLineIO("Ошибка аргумент должен быть целым числом\n");
            }
        }
        managerInputOutput.writeLineIO("Неверное количество аргументов");
        return false;
    }
    @Override
    public String toString() {
        return "update_id - обновляет значение элемента коллекции, id которого равен заданному\n";
    }

}