package org.example.server.commands;

import org.example.server.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;


/**
 * Команда info - выводит информацию о коллекции.
 */
public class Info implements Command {
    private CollectionManager collectionManager;

    /**
     * Создает команду info.
     *
     * @param collectionManager менеджер коллекции для получения информации
     */
    public Info(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }


    public ResponsePacket executeCommand(CommandPacket commandPacket){
        if(commandPacket.getArgs() != null && commandPacket.getArgs().length > 0){
            return new ResponsePacket("Команда info не принимает аргументы ", null);
        }

        Map<String, Object> info = new HashMap<>();
        info.put("size", collectionManager.getCollection().size());
        info.put("initTime", collectionManager.getDateTime());
        info.put("collectionType", collectionManager.getCollection().getClass().getSimpleName());
        info.put("elementType", "Person");
        return new ResponsePacket("Информация о коллекции получена.", info);
    }
}