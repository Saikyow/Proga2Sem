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
 * Команда replace_if_greater - заменяет значение по ключу, если новое значение больше старого.
 * Сравнение производится по полю weight.
 */
public class ReplaceIfGreater implements Command {
    private final ManagerCreatorPerson managerCreatorPerson = new ManagerCreatorPerson();

    /**
     * Создает команду replace_if_greater.
     *
     * @param collectionManager менеджер коллекции
     * @param personAsker объект для создания нового Person
     */

    /**
     * Проверяет аргументы команды.
     *
     * @param args массив аргументов (должен содержать ключ)
     * @return true, если аргумент корректен
     */
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
     * Выполняет команду replace_if_greater.
     * Сравнивает вес нового и старого элемента. Если новый вес больше, производит замену.
     *
     * @param args аргументы команды (ключ)
     */
    public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {
        if (checkArg(args)) {
            Person person = managerCreatorPerson.createPerson();
            CommandPacket commandPacket = new CommandPacket("replace_if_greater", args, person);


                writeModule.writePacketForServer(serverChannel, commandPacket);

                ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);

                if (responsePacket != null){
                    managerInputOutput.writeLineIO("Сервер: " + responsePacket.getMessage());
                }

        }else {
            managerInputOutput.writeLineIO("Неверное количество аргументов ");
        }

    }




    @Override
    public String toString(){
        return "replace_if_greater - заменяет значение по ключу, если новое значение больше старого";
    }
}