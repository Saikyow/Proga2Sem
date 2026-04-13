package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Show implements Command {

    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket packet) {

        if (packet.getArgs() != null && packet.getArgs().length > 0) {
            return new ResponsePacket("Команда show не принимает аргументы.", null);
        }

        LinkedHashMap<Long, Person> sorted = collectionManager.getCollection()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> {
                    Person p = entry.getValue();
                    if (p.getLocation() == null) return "";
                    return p.getLocation().getName() == null ? "" : p.getLocation().getName();
                }))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        if (sorted.isEmpty()) {
            return new ResponsePacket("Коллекция пуста.", null);
        }

        return new ResponsePacket("Коллекция:", sorted);
    }
}