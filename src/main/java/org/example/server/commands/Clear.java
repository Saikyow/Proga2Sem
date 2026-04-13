package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

public class Clear implements Command {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket commandPacket){

        if (commandPacket.getArgs() != null && commandPacket.getArgs().length > 0){
            return new ResponsePacket("Команда clear не принимает аргументы ", null);
        }

        collectionManager.clearCollection();
        return new ResponsePacket("Коллекция очищена", null);

    }
}