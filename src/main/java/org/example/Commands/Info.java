package org.example.Commands;

import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import static org.example.Runner.Runner.managerInputOutput;

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
        managerInputOutput.writeLineIO("Ошибка! Команда Help не принимает аргументы. \n");
        return false;
    }

    /**
     * Выполняет команду info.
     * Выводит тип коллекции, количество элементов и другую информацию.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args){
        if (!checkArg(args)){
            return;
        }

        var collections = collectionManager.getCollections();

        managerInputOutput.writeLineIO("Информация о коллекции: \n");
        if (collections.isEmpty()){
            managerInputOutput.writeLineIO("Коллекция пустая\n");
        }else{
            managerInputOutput.writeLineIO("В коллекции есть элементы.\n");
        }
        managerInputOutput.writeLineIO("------------------------------------------------------\n");

        managerInputOutput.writeLineIO("Тип коллекции: " + collections.getClass().getName() + "\n");
        managerInputOutput.writeLineIO("Количество элементов: " + collections.size() + "\n");

        managerInputOutput.writeLineIO("------------------------------------------------------\n");
    }

    @Override
    public String toString() {
        return "info - выводит информацию о коллекции в стандартный поток вывода";
    }
}