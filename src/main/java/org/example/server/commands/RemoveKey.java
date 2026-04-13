package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

public class RemoveKey implements Command {

    private final CollectionManager collectionManager;

    public RemoveKey(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket packet) {

        if (packet.getArgs() == null || packet.getArgs().length != 1) {
            return new ResponsePacket("Команда remove_key требует один аргумент: key.", null);
        }

        Long key;
        try {
            key = Long.parseLong(packet.getArgs()[0]);
        } catch (NumberFormatException e) {
            return new ResponsePacket("Ключ должен быть числом.", null);
        }

        if (!collectionManager.getCollection().containsKey(key)) {
            return new ResponsePacket("Элемент с ключом " + key + " не найден.\n", null);
        }

        collectionManager.remove(key);

        return new ResponsePacket("Элемент с ключом " + key + " удалён.\n", null);
    }
}