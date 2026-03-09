package org.example.Runner;

import org.example.User.PersonAsker;
import org.example.managers.CollectionManager;
import org.example.managers.ManagerInputOutput;
import org.example.managers.ManagerParserCommand;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Главный класс приложения, отвечающий за запуск и выполнение основного цикла команд.
 */
public class Runner {
    public static ManagerParserCommand managerParserCommand;
    public static ManagerInputOutput managerInputOutput;
    public static CollectionManager collectionManager;
    public static PersonAsker personAsker;
    public static String fileName;

    /**
     * Конструктор, инициализирующий все необходимые менеджеры и утилиты.
     */
    public Runner(String fileName) {
        managerInputOutput = ManagerInputOutput.getInstance();
        collectionManager = new CollectionManager(fileName);
        personAsker = new PersonAsker();
        managerParserCommand = new ManagerParserCommand(collectionManager, personAsker);

        List<String> commandNames = managerParserCommand.getCommandNames();
        managerInputOutput.setCommands(commandNames);

    }

    /**
     * Запускает основной цикл обработки команд пользователя.
     * Читает команды из ввода, передает их парсеру и обрабатывает возможное исключение.
     */
    public void run() {
        try {
            while (true) {
                // Добавим проверку, что ввод доступен
                if (System.in.available() == 0) {
                    // Небольшая задержка для стабильности
                    Thread.sleep(10);
                }

                String command = managerInputOutput.readLineIO("Введите команду: ");

                if (command == null) {
                    System.out.println("Получен null (возможно конец ввода)");
                    continue;
                }

                if (command.isBlank()){
                    continue;
                }

                boolean flag = managerParserCommand.parserCommand(command);
                if (!flag) {
                    managerInputOutput.writeLineIO("Неизвестная команда\n");
                }
            }
        } catch (NoSuchElementException e) {
            managerInputOutput.writeLineIO("Обнаружен конец ввода. Завершение...\n");
            managerInputOutput.closeIO();
        } catch (InterruptedException e) {
            managerInputOutput.writeLineIO("Программа прервана\n");
        } catch (Exception e) {
            managerInputOutput.writeLineIO("Ошибка: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}