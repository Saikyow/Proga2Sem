package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

import java.util.Map;

public class UpdateID implements Command {

    private final CollectionManager collectionManager;

    public UpdateID(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket commandPacket){
        if (commandPacket.getArgs() == null && commandPacket.getArgs().length != 1) {
            return new ResponsePacket("Команда update_id требует 1 аргумент id \n", null);
        }

        Long id;
        try {
            id = Long.parseLong(commandPacket.getArgs()[0]);
        } catch (NumberFormatException e) {
            return new ResponsePacket("id должен быть числом.", null);
        }

        Person newPerson = commandPacket.getValues();
        if (newPerson == null) {
            return new ResponsePacket("id должен быть числом.", null);
        }

        Long targetKey = null;

        for (Map.Entry<Long, Person> entry : collectionManager.getCollection().entrySet()) {
            Person person = entry.getValue();
            if (person.getId() == id){
                targetKey = entry.getKey();
                break;
            }
        }
        if (targetKey == null) {
            return new ResponsePacket("Элемент с id = " + id + " не найден.", null);
        }
        collectionManager.getCollection().put(targetKey, newPerson);
        return new ResponsePacket("Элемент с id = " + id + " успешно обновлён.", null);
    }
}