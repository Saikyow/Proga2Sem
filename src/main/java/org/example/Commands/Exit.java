package org.example.Commands;

import org.example.interfaces.Command;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда exit - завершает программу без сохранения в файл.
 */
public class Exit implements Command {

    /**
     * Выполняет команду exit.
     * Закрывает все ресурсы ввода-вывода и завершает работу программы.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args){
        if (checkArg(args)) {
            managerInputOutput.closeIO();
            System.exit(0);
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
    public boolean checkArg(String[] args){
        return args.length == 0;
    }

    @Override
    public String toString(){
        return "exit - завершает программу без сохранения в файл";
    }
}