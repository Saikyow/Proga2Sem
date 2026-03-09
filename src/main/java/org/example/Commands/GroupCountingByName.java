package org.example.Commands;

import org.example.User.Person;
import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;

import static org.example.Runner.Runner.managerInputOutput;

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

    /**
     * Выполняет команду group_counting_by_name.
     * Группирует элементы коллекции по значению поля name и выводит количество элементов в каждой группе.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args){
        if (!checkArg(args)){
            return;
        }
        Map<Long, Person> collections = collectionManager.getCollections();

        if (collections.isEmpty()){
            managerInputOutput.writeLineIO("Коллекция пустая. \n");
            return;
        }

        Map<String, Integer> groups = new HashMap<>();

        for (Person person : collections.values()){
            String name = person.getName();
            groups.put(name, groups.getOrDefault(name, 0) + 1);
        }

        groups.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String name = entry.getKey();
                    int count = entry.getValue();
                    managerInputOutput.writeLineIO("Имя: " + name + " | Количество: " + count + "\n");
                });
    }

    /**
     * Проверяет аргументы команды.
     *
     * @param args массив аргументов
     * @return true, если аргументов нет
     */
    private boolean checkArg(String[] args){
        if (args.length == 0 || args == null){
            return true;
        }
        managerInputOutput.writeLineIO("Ошибка! Команда clear не принимает аргументы. \n");
        return false;
    }

    @Override
    public String toString(){
        return "group_counting_by_name - группирует элементы коллекции по значению поля name, выводит количество элементов в каждой группе";
    }
}