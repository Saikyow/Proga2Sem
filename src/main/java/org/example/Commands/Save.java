package org.example.Commands;

import org.example.User.Person;
import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда save - сохраняет коллекцию в CSV файл.
 */
public class Save implements Command {
    private CollectionManager collectionManager;

    /**
     * Создает команду save.
     *
     * @param collectionManager менеджер коллекции для сохранения
     */
    public Save(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду save.
     * Сохраняет текущую коллекцию в файл "collection.csv".
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args) {
        if (!checkArg(args)) {
            return;
        }

        String filename = collectionManager.getFileName();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("key;id;name;coordinates_x;coordinates_y;creationDate;height;weight;hairColor;nationality;" +
                    "location_x;location_y;location_z;location_name");

            var collections = collectionManager.getCollections();

            for (Map.Entry<Long, Person> entry : collections.entrySet()) {
                Long key = entry.getKey();
                Person person = entry.getValue();

                String csvLine = String.format("%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",
                        key,
                        person.getId(),
                        person.getName(),
                        person.getCoordinates().getX(),
                        person.getCoordinates().getY(),
                        person.getCrationDate().toString(),
                        person.getHeight() != null ? person.getHeight() : "",
                        person.getWeight() != null ? person.getWeight() : "",
                        person.getHairColor() != null ? person.getHairColor() : "",
                        person.getNationality(),
                        person.getLocation() != null ? person.getLocation().getX() : "",
                        person.getLocation() != null ? person.getLocation().getY() : "",
                        person.getLocation() != null ? person.getLocation().getZ() : "",
                        person.getLocation() != null ? person.getLocation().getName() : ""
                );
                writer.println(csvLine);
            }

            managerInputOutput.writeLineIO("Коллекция успешно сохранена в файл " + filename + "\n");

        } catch (IOException e){
            managerInputOutput.writeLineIO("Ошибка при сохранении " + e.getMessage());
        }
    }

    /**
     * Проверяет аргументы команды.
     *
     * @param args массив аргументов
     * @return true, если аргументов нет
     */
    public boolean checkArg(String[] args) {
        if (args.length == 0 || args == null) {
            return true;
        } else {
            managerInputOutput.writeLineIO("Команда show не принимает аргументы \n");
            return false;
        }
    }

    @Override
    public String toString(){
        return "save - сохранить коллекцию в файл";
    }
}