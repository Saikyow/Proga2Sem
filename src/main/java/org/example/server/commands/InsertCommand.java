package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.ManagerGenerateId;

import java.time.ZonedDateTime;


/**
 * Команда insert - добавляет новый элемент с заданным ключом.
 */
public class InsertCommand implements Command {
    private CollectionManager collectionManager;


    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket commandPacket) {
        if (commandPacket.getArgs() == null || commandPacket.getArgs().length != 1) {
            return new ResponsePacket("Нужно указать один ключ.", null);
        }
        Long key;
        try {
            key = Long.parseLong(commandPacket.getArgs()[0]);
        } catch (NumberFormatException e) {
            return new ResponsePacket("Ключ должен быть числом.", null);
        }

        Person clientPerson = commandPacket.getValues();
        if (clientPerson == null) {
            return new ResponsePacket("Объект Person не передан.", null);
        }
        Person serverPerson = new Person(
                ManagerGenerateId.generateId(),
                clientPerson.getName(),
                clientPerson.getCoordinates(),
                ZonedDateTime.now(),
                clientPerson.getHeight(),
                clientPerson.getWeight(),
                clientPerson.getHairColor(),
                clientPerson.getNationality(),
                clientPerson.getLocation()
                );
        if (collectionManager.getCollection().containsKey(key)) {
            return new ResponsePacket("Элемент с таким ключом уже существует.\n", null);
        }
        collectionManager.addCollection(key, serverPerson);

        return new ResponsePacket("Элемент успешно добавлен.", null);
    }

}