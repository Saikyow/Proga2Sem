package org.example.Commands;

import org.example.interfaces.Command;
import org.example.managers.ManagerParserCommand;

import java.util.List;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда history - выводит последние 14 выполненных команд.
 */
public class History implements Command {

    private ManagerParserCommand managerParserCommand;

    /**
     * Создает команду history.
     *
     * @param managerParserCommand парсер команд для получения истории
     */
    public History(ManagerParserCommand managerParserCommand){
        this.managerParserCommand = managerParserCommand;
    }

    /**
     * Выполняет команду history.
     * Выводит список последних 14 команд (без аргументов).
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args){
        if (!checkArg(args)){
            return;
        }

        List<String> history = managerParserCommand.getHistoryCommands();

        if (history.isEmpty()){
            return;
        }
        managerInputOutput.writeLineIO("Последние команды:\n");
        managerInputOutput.writeLineIO("------------------------------------------------------\n");
        for (int i = 0; i<history.size(); i++){
            managerInputOutput.writeLineIO((i+1) + ". " + history.get(i) + "\n");
        }
        managerInputOutput.writeLineIO("------------------------------------------------------\n");
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
        managerInputOutput.writeLineIO("Ошибка! Команда history не принимает аргументы. \n");
        return false;
    }

    @Override
    public String toString(){
        return "history - выводит последние 14 команд без их аргументов";
    }
}