package org.example.Commands;

import org.example.interfaces.Command;

import static org.example.Runner.Runner.managerInputOutput;
import static org.example.Runner.Runner.managerParserCommand;

/**
 * Команда help - выводит справку по доступным командам.
 */
public class Help implements Command {

    /**
     * Выполняет команду help.
     * Выводит список всех доступных команд с их описанием.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args) {
        if (checkArg(args)) {
            managerInputOutput.writeLineIO("Справка по командам:\n");
            managerInputOutput.writeLineIO("------------------------------------------------------\n");
            for (Command cmd : managerParserCommand.getCommand()) {
                managerInputOutput.writeLineIO(cmd + "\n");
            }
            managerInputOutput.writeLineIO("------------------------------------------------------\n");
        } else {
            managerInputOutput.writeLineIO("Неверное количество аргументов\n");
        }
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

    @Override
    public String toString() {
        return "help - выводит справку по каждой программе";
    }
}