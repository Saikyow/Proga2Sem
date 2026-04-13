package org.example.server.commands;

import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.packet.request.ResponsePacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

public class ReplaceIfLowe implements Command {

    private final CollectionManager collectionManager;

    public ReplaceIfLowe(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket packet) {

        if (packet.getArgs() == null || packet.getArgs().length != 1) {
            return new ResponsePacket("Команда replace_if_greater требует ключ.", null);
        }

        Long key;
        try {
            key = Long.parseLong(packet.getArgs()[0]);
        } catch (NumberFormatException e) {
            return new ResponsePacket("Ключ должен быть числом.", null);
        }

        if (!collectionManager.getCollection().containsKey(key)) {
            return new ResponsePacket("Элемент с ключом " + key + " не найден.", null);
        }

        Person newPerson = packet.getValues();
        if (newPerson == null) {
            return new ResponsePacket("Новый объект Person не передан.", null);
        }

        Person oldPerson = collectionManager.getCollection().get(key);

        Float oldWeight = oldPerson.getWeight();
        Float newWeight = newPerson.getWeight();

        if (newWeight == null) {
            return new ResponsePacket("У нового элемента weight не может быть null.", null);
        }

        if (oldWeight == null) {
            collectionManager.getCollection().put(key, newPerson);
            return new ResponsePacket("Элемент заменён (старый weight был null).", null);
        }

        if (oldWeight > newWeight) {
            collectionManager.getCollection().put(key, newPerson);
            return new ResponsePacket("Элемент успешно заменён.", null);
        } else {
            return new ResponsePacket("Замена не выполнена: новый weight меньше или равен старому.", null);
        }
    }
}