package org.example.managers;

import org.example.Commands.*;
import org.example.User.PersonAsker;
import org.example.interfaces.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

/**
 * Парсит и выполняет команды, введенные пользователем.
 * Хранит историю выполненных команд.
 */
public class ManagerParserCommand {
    private final HashMap<String, Command> commands;
    private final List<String> historyCommands;
    private static final int MAX_HISTORY_SIZE = 14;

    /**
     * Создает парсер команд и регистрирует все доступные команды.
     *
     * @param collectionManager менеджер коллекции
     * @param personAsker       объект для создания Person
     */
    public ManagerParserCommand(CollectionManager collectionManager, PersonAsker personAsker) {
        this.commands = new HashMap<>();
        this.historyCommands = new ArrayList<>(MAX_HISTORY_SIZE);

        this.commands.put("help", new Help());
        this.commands.put("info", new Info(collectionManager));
        this.commands.put("show", new Show(collectionManager));
        this.commands.put("insert", new InsertCommand(collectionManager, personAsker));
        this.commands.put("update_id", new UpdateID(collectionManager, personAsker));
        this.commands.put("remove_key", new RemoveKey(collectionManager));
        this.commands.put("clear", new Clear(collectionManager));
        this.commands.put("save", new Save(collectionManager));
        this.commands.put("execute_script", new ExecuteScript());
        this.commands.put("exit", new Exit());
        this.commands.put("history", new History(this));
        this.commands.put("replace_if_greater", new ReplaceIfGreater(collectionManager, personAsker));
        this.commands.put("replace_if_lowe", new ReplaceIfLowe(collectionManager, personAsker));
        this.commands.put("sum_of_weight", new SumOfWeight(collectionManager));
        this.commands.put("group_counting_by_name", new GroupCountingByName(collectionManager));
        this.commands.put("print_ascending", new PrintAscending(collectionManager));
    }

    /**
     * Парсит и выполняет команду.
     *
     * @param s строка с командой и аргументами
     * @return true, если команда распознана и выполнена
     */


    public boolean parserCommand(String s) {
        String[] command = s.trim().replaceAll("\\s+", " ").split(" ");

        if (this.commands.containsKey(command[0])) {
            System.out.println(command[0]);
            Command cmd = this.commands.get(command[0]);
            cmd.executeCommand(Arrays.copyOfRange(command, 1, command.length));

            if (this.historyCommands.size() >= MAX_HISTORY_SIZE) {
                this.historyCommands.remove(0);
            }
            this.historyCommands.add(s);
            return true;
        }
        return false;
    }
    public List<String> getCommandNames() {
        List<String> names = new ArrayList<>();
        for (String key : this.commands.keySet()) {
            names.add(key);
        }
        return names;
    }

    /**
     * Возвращает список всех зарегистрированных команд.
     *
     * @return список объектов Command
     */
    public List<Command> getCommand() {
        return new ArrayList<>(this.commands.values());
    }

    /**
     * Возвращает историю выполненных команд.
     *
     * @return список строк с командами
     */
    public List<String> getHistoryCommands() {
        return historyCommands;
    }
}