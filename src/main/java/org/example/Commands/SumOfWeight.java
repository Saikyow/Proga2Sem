package org.example.Commands;

import org.example.User.Person;
import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда sum_of_weight - выводит сумму значений поля weight для всех элементов коллекции.
 */
public class SumOfWeight implements Command {

    private CollectionManager collectionManager;

    /**
     * Создает команду sum_of_weight.
     *
     * @param collectionManager менеджер коллекции
     */
    public SumOfWeight(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду sum_of_weight.
     * Суммирует значения поля weight всех элементов коллекции.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args) {
        if (!checkArg(args)){
            return;
        }

        var collections = collectionManager.getCollections();
        float totalWeight = 0;

        if (collections.isEmpty()){
            managerInputOutput.writeLineIO("Коллекция пустая \n");
            return;
        }

        for (Person person : collections.values()){
            Float weight = person.getWeight();
            if (weight != null){
                totalWeight += weight;
            }
        }

        managerInputOutput.writeLineIO("Общий вес всех героев коллекции = " + totalWeight + "\n");
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
        managerInputOutput.writeLineIO("Ошибка! Команда sum_of_weight не принимает аргументы. \n");
        return false;
    }

    @Override
    public String toString(){
        return "sum_of_weight - выводит сумму значений поля weight для всех элементов коллекции";
    }
}