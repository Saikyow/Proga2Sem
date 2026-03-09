package org.example.Commands;

import org.example.User.Person;
import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import java.util.Map;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда show - выводит все элементы коллекции в строковом представлении.
 */
public class Show implements Command {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Проверяет аргументы команды.
     *
     * @param args аргументы
     * @return true, если аргументов нет
     */
    public boolean checkArg(String[] args) {
        if (args.length == 0 || args == null) {
            return true;
        } else {
            managerInputOutput.writeLineIO("Команда show не принимает аргументы \n");
            return false;
        }
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы (не ожидаются)
     */
    public void executeCommand(String[] args) {
        if (!checkArg(args)) {
            return;
        }

        Map<Long, Person> collections = collectionManager.getCollections();
        if (collections.isEmpty()) {
            managerInputOutput.writeLineIO("Коллекция пустая \n");
            return;
        }
        for (Map.Entry<Long, Person> entry : collections.entrySet()) {
            Long key = entry.getKey();
            Person person = entry.getValue();
            managerInputOutput.writeLineIO("Ключ: " + key + " name: " + person.getName() + " Вес: " + person.getWeight() +
                    " id: " + person.getId() + " Время создания " + person.getCrationDate() + person.getLocation() + "\n");
        }
    }

    @Override
    public String toString() {
        return "show - выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}