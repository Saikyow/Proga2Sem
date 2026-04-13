package org.example.server.commands;



import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;


/**
 * Команда group_counting_by_name - группирует элементы по имени и выводит количество в каждой группе.
 */
public class GroupCountingByName implements Command {
    private CollectionManager collectionManager;

    /**
     * Создает команду group_counting_by_name.
     *
     * @param collectionManager менеджер коллекции
     */
    public GroupCountingByName(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket commandPacket) {

        if (commandPacket.getArgs() != null && commandPacket.getArgs().length > 0) {
            return new ResponsePacket("Команда group_counting_by_name не принимает аргументы.", null);
        }
        Map<String, Integer> result = new HashMap<>();

        for(Person person : collectionManager.getCollection().values()){
            String name = person.getName();
            result.put(name, result.getOrDefault(name, 0) + 1);
        }
        if(result.isEmpty()){
            return new ResponsePacket("Коллекция пустая ", null);
        }

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Integer> entry : result.entrySet()){
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return new ResponsePacket(sb.toString(), null);

    }
}