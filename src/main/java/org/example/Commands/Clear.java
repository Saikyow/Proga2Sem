package org.example.Commands;

import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда clear - очищает коллекцию.
 */
public class Clear implements Command {
    private CollectionManager collectionManager;

    /**
     * Создает команду clear.
     *
     * @param collectionManager менеджер коллекции
     */
    public Clear(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду clear.
     * Удаляет все элементы из коллекции.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args){
        if (!checkArg(args)){
            return;
        }
        if (collectionManager == null){
            managerInputOutput.writeLineIO("Ошибка! collectionManager не инициализирован.\n");
            return;
        }
        collectionManager.clearCollections();

        managerInputOutput.writeLineIO("Коллекция успешно удалена. \n");
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
        return "clear - очистить коллекцию";
    }
}