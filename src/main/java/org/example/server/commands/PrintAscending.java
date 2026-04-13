package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;

import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending implements Command {

    private final CollectionManager collectionManager;

    public PrintAscending(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket packet) {

        if (packet.getArgs() != null && packet.getArgs().length > 0) {
            return new ResponsePacket("Команда print_ascending не принимает аргументы.\n", null);
        }

        List<Person> sorted = collectionManager.getCollection()
                .values()
                .stream()
                .sorted()
                .collect(Collectors.toList());

        if (sorted.isEmpty()) {
            return new ResponsePacket("Коллекция пуста.", null);
        }

        return new ResponsePacket("Элементы в порядке возрастания:", sorted);
    }
}