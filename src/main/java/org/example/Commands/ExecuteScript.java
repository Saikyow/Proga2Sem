package org.example.Commands;

import org.example.interfaces.Command;
import org.example.managers.ManagerParserCommand;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.example.Runner.Runner.managerInputOutput;
import static org.example.Runner.Runner.managerParserCommand;


/**
 * Команда execute_script - считывает и исполняет скрипт из указанного файла.
 */

public class ExecuteScript implements Command {
    private ManagerParserCommand parserCommand;
    private int lineNumer = 0;
    private static final Set<String> setPaths = new HashSet<String>();


    /**
     * Проверяет аргументы команды.
     *
     * @param args аргументы (должен быть путь к файлу)
     * @return true, если аргумент передан
     */
    public boolean checkArg(String[] args) {
        if (args == null || args.length == 0 || args[0].isEmpty()) {
            managerInputOutput.writeLineIO("Не указан путь скрипта \n");
            return false;
        }
        return true;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы (путь к файлу скрипта)
     */
    public void executeCommand(String[] args){
        if (!checkArg(args)) {
            managerInputOutput.writeLineIO("Ошибка! Команда должна принимать аргументы. \n");
            return;
        }

        String fileName = args[0];

        File file = new File("/src" + fileName);

        if (!file.exists()) {
            file = new File(fileName);
        }

        String pathFile = file.getAbsolutePath();


        if (!file.exists()){
            managerInputOutput.writeLineIO("Файл не найден. \n");
            return;
        }
        if (!file.canRead()){
            managerInputOutput.writeLineIO("Недостаточно прав для чтения файла. \n");
            return;
        }

        if (setPaths.contains(pathFile)){
            managerInputOutput.writeLineIO("Ошибка! Рекурсия в скрипте. \n");
            return;
        }



        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            setPaths.add(pathFile);

            managerInputOutput.pushFileExecute(reader);

            lineNumer = 0;

            String line;

            while (true){
                line = managerInputOutput.readLineIO();

                if (line == null){break;}

                if (!managerInputOutput.isCurrentReader(reader)){break;}

                lineNumer++;
                line = line.trim();
                if (line.isEmpty()){continue;}


                if (line.equals("exit")){
                    managerInputOutput.writeLineIO("Команда exit в скрипте запрещена.\n");
                    continue;}

                managerParserCommand.parserCommand(line);

                if (!managerInputOutput.isCurrentReader(reader)){break;}

            }

            if (managerInputOutput.isCurrentReader(reader)){
                managerInputOutput.popFileExecute();
            }
            managerInputOutput.writeLineIO("Скрипт " + fileName + " выполнен!\n");



        }catch (IOException e){
            managerInputOutput.writeLineIO("Ошибка " + e.getMessage() + "\n");
        }finally {
            setPaths.remove(pathFile);
        }

    }

    @Override
    public String toString(){return "execute_script - считывает и исполняет скрипт из указанного файла.";}
}
