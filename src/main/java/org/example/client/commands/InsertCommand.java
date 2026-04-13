package org.example.client.commands;


import org.example.client.interfaces.Command;
import org.example.client.managers.ManagerCreatorPerson;
import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;
/**
 * Команда insert - добавляет новый элемент с заданным ключом.
 */
public class InsertCommand implements Command {

    private final ManagerCreatorPerson managerCreatorPerson =  new ManagerCreatorPerson();

    public boolean checkArg(String[] args) {
        if (args == null || args.length == 0 || args[0].isEmpty()) {
            managerInputOutput.writeLineIO("Не указан ключ \n");
            return false;
        }
        try {
            Long.parseLong(args[0]);
            return true;
        } catch (NumberFormatException e) {
            managerInputOutput.writeLineIO("Ключ должен быть целым числом. Вы ввели: " + args[0]);
            return false;
        }
    }

    /**
     * Выполняет команду insert.
     * Создает нового Person и добавляет его в коллекцию по указанному ключу.
     *
     * @param args аргументы команды (ключ)
     */
    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {
        if (checkArg(args)){

            Person person = managerCreatorPerson.createPerson();

            CommandPacket commandPacket = new CommandPacket("insert", args, person);


                writeModule.writePacketForServer(serverChannel, commandPacket);

                ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);

                if (responsePacket != null){
                    managerInputOutput.writeLineIO("Сервер: " + responsePacket.getMessage() + "\n");
                }

        }else {
            managerInputOutput.writeLineIO("Неверное количество аргументов ");
        }
    }

    @Override
    public String toString(){
        return "insert - добавляет новый элемент с заданным ключом";
    }
}