package org.example.client.commands;

import org.example.client.interfaces.Command;
import org.example.client.managers.ManagerInputOutput;
import org.example.client.managers.ManagerParserClient;

import java.nio.channels.SocketChannel;

/** Команда help - выводит справку по доступным командам. */
public class Help implements Command {
  private final ManagerInputOutput managerInputOutput = ManagerInputOutput.getInstance();
  private final ManagerParserClient managerParserCommand;

  public Help(ManagerParserClient managerParserCommand) {
    this.managerParserCommand = managerParserCommand;
  }

  /**
   * Выполняет команду help. Выводит список всех доступных команд с их описанием.
   *
   * @param args аргументы команды (не ожидаются)
   */
  public void executeCommand(String[] args, SocketChannel serverChannel) {
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
  private boolean checkArg(String[] args) {
    if (args == null || args.length == 0) {
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

