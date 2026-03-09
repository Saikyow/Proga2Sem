package org.example;

import org.example.Runner.Runner;

/**
 * Главный класс приложения, точка входа.
 */

public class Main {


    /**
     * Точка входа в программу.
     * Создает и запускает основной цикл обработки команд.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Ошибка: не указано имя файла с коллекцией.");
            System.out.println("Пример использования: java -jar build/libs/Lab5.jar collection1.csv");
            return;
        }

        String fileName = args[0];
        Runner run = new Runner(args[0]);
        run.run();
    }
}

