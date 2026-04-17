package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.csvParserManager;

public class Save implements Command {
    private final CollectionManager collectionManager;
    private final String filePath;
    private final csvParserManager csvManager;

    public Save(CollectionManager collectionManager, String filePath) {
        this.collectionManager = collectionManager;
        this.filePath = filePath;
        this.csvManager = csvParserManager.getInstance();
    }

    public ResponsePacket execute() {
        boolean success = csvManager.writeCSV(filePath, collectionManager.getCollection());

        if (success) {
            return new ResponsePacket("Коллекция успешно сохранена.", null);
        } else {
            return new ResponsePacket("Не удалось сохранить коллекцию.", null);
        }
    }

    @Override
    public ResponsePacket executeCommand(CommandPacket commandPacket) {
        if (commandPacket.getArgs() != null && commandPacket.getArgs().length > 0) {
            return new ResponsePacket("Команда save не принимает аргументы.", null);
        }

        return execute();
    }
}