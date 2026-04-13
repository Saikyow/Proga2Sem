package org.example.server.commands;

import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Person;
import org.example.packet.request.CommandPacket;
import org.example.server.interfaces.Command;
import org.example.server.managers.CollectionManager;


/**
 * Команда sum_of_weight - выводит сумму значений поля weight для всех элементов коллекции.
 */
public class SumOfWeight implements Command {

    private CollectionManager collectionManager;

    public SumOfWeight(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ResponsePacket executeCommand(CommandPacket packet) {

        if (packet.getArgs() != null && packet.getArgs().length > 0) {
            return new ResponsePacket("Команда show не принимает аргументы.", null);
        }

        double sum = collectionManager.getCollection()
                .values()
                .stream()
                .map(Person::getWeight)
                .filter(weight -> weight != null)
                .mapToDouble(Float::doubleValue)
                .sum();

        return new ResponsePacket("Сумма weight: " + sum + "\n", null);
    }

}