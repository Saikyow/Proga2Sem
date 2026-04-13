    package org.example.client.managers;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;

    import org.example.client.commands.Clear;
    import org.example.client.commands.ExecuteScript;
    import org.example.client.commands.Exit;
    import org.example.client.commands.GroupCountingByName;
    import org.example.client.commands.Help;
    import org.example.client.commands.History;
    import org.example.client.commands.Info;
    import org.example.client.commands.InsertCommand;
    import org.example.client.commands.PrintAscending;
    import org.example.client.commands.RemoveKey;
    import org.example.client.commands.ReplaceIfGreater;
    import org.example.client.commands.ReplaceIfLowe;
    import org.example.client.commands.Save;
    import org.example.client.commands.Show;
    import org.example.client.commands.SumOfWeight;
    import org.example.client.commands.UpdateID;
    import org.example.client.interfaces.Command;

    import static org.example.client.Client.*;

    /** Парсит и выполняет команды, введенные пользователем. Хранит историю выполненных команд. */
    public class ManagerParserClient {
      private final HashMap<String, Command> commands;
      private final List<String> historyCommands;
      private static final int MAX_HISTORY_SIZE = 14;

      public ManagerParserClient() {
        this.commands = new HashMap<>();
        this.historyCommands = new ArrayList<>(MAX_HISTORY_SIZE);

        this.commands.put("help", new Help(this));
        this.commands.put("info", new Info());
        this.commands.put("show", new Show());
        this.commands.put("insert", new InsertCommand());
        this.commands.put("update_id", new UpdateID());
        this.commands.put("remove_key", new RemoveKey());
        this.commands.put("clear", new Clear());
        this.commands.put("save", new Save());
        this.commands.put("execute_script", new ExecuteScript());
        this.commands.put("exit", new Exit(ManagerInputOutput.getInstance()));
        this.commands.put("history", new History());
        this.commands.put("replace_if_greater", new ReplaceIfGreater());
        this.commands.put("replace_if_lowe", new ReplaceIfLowe());
        this.commands.put("sum_of_weight", new SumOfWeight());
        this.commands.put("group_counting_by_name", new GroupCountingByName());
        this.commands.put("print_ascending", new PrintAscending());
      }

       public void parserCommand(String s) throws IOException, ClassNotFoundException {
          if (s == null || s.trim().isEmpty()) {
               return;
           }
          String[] command = s.trim().replaceAll("\\s+", " ").split(" ");

          String commandName = command[0];
          String[] args = Arrays.copyOfRange(command, 1, command.length);

          if (this.commands.containsKey(commandName)) {
              Command cmd = this.commands.get(commandName);

              if (this.historyCommands.size() >= MAX_HISTORY_SIZE) {
                this.historyCommands.remove(0);
            }
            this.historyCommands.add(s);


              if (!commandName.equals("help")
                      && !commandName.equals("history")
                      && !commandName.equals("execute_script")
                      && !commandName.equals("exit")
                      && (server == null || !server.isOpen())) {
                  managerInputOutput.writeLineIO("Нет соединения с сервером");
                  return;
              }



              if (!commandName.equals("exit") && (server == null || !server.isOpen())) {
                managerInputOutput.writeLineIO("Нет соединения с сервером");
                return;
            }

            cmd.executeCommand(args, server);

        }else{
            managerInputOutput.writeLineIO("Неизвестная команда\n");
        }


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

