package org.example.client.commands;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import org.example.client.interfaces.Command;
import org.example.client.managers.ManagerInputOutput;

/** Команда exit - завершает программу без сохранения в файл. */
public class Exit implements Command {

  private final ManagerInputOutput io;

  public Exit(ManagerInputOutput io) {
    this.io = io;
  }

  /**
   * Выполняет команду exit. Закрывает все ресурсы ввода-вывода и завершает работу программы.
   *
   * @param args аргументы команды (не ожидаются)
   */
  public void executeCommand(String[] args, SocketChannel serverChannel) throws IOException, ClassNotFoundException {

      if (checkArg(args)) {
        io.closeIO();
          if (serverChannel != null) {
              serverChannel.close();
          }
          System.exit(0);
      } else {
        ManagerInputOutput.getInstance().writeLineIO("Неверное количество аргументов\n");
      }

  }

  /**
   * Проверяет аргументы команды.
   *
   * @param args массив аргументов
   * @return true, если аргументов нет
   */
  public boolean checkArg(String[] args) {
    return args.length == 0;
  }

  @Override
  public String toString() {
    return "exit - завершает программу без сохранения в файл";
  }
}

