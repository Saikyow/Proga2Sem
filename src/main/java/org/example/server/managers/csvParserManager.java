package org.example.server.managers;

import org.example.packet.element.Location;
import org.example.packet.element.Person;
import org.example.server.logger.ServerLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class csvParserManager {
    private static csvParserManager csvParserManager;

    private csvParserManager() {}

    public static csvParserManager getInstance() {
        if (csvParserManager == null) {
            csvParserManager = new csvParserManager();
        }
        return csvParserManager;
    }

    public static List<String[]> readCSV(String filePath) {
        if (filePath == null) {
            return new ArrayList<>();
        }

        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            ServerLogger.error("Файл не найден", filePath);
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(";", -1);
                data.add(fields);
            }

            ServerLogger.info("Загружено строк: {}", data.size());

        } catch (IOException e) {
            ServerLogger.error("Ошибка чтения файла: {}", e.getMessage());
        }

        return data;
    }

    public boolean writeCSV(String filePath, HashMap<Long, Person> persons) {
        if (filePath == null || filePath.trim().isEmpty()) {
            ServerLogger.error("Ошибка: путь не указан");
            return false;
        }

        if (!filePath.toLowerCase().endsWith(".csv")) {
            ServerLogger.error("Ошибка: файл должен иметь расширение .csv");
            return false;
        }

        File file = new File(filePath);

        try (OutputStreamWriter writer =
                     new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {

            writer.write("key;id;name;x;y;creationDate;height;weight;hairColor;nationality;locationX;locationY;locationZ;locationName\n");

            for (Map.Entry<Long, Person> entry : persons.entrySet()) {

                Long key = entry.getKey();
                Person person = entry.getValue();

                Location location = person.getLocation();

                String locationX = "";
                String locationY = "";
                String locationZ = "";
                String locationName = "";

                if (location != null) {
                    locationX = String.valueOf(location.getX());
                    locationY = String.valueOf(location.getY());
                    locationZ = String.valueOf(location.getZ());
                    locationName = location.getName() == null ? "" : location.getName();
                }

                String line =
                        key + ";" +
                                person.getId() + ";" +
                                person.getName() + ";" +
                                person.getCoordinates().getX() + ";" +
                                person.getCoordinates().getY() + ";" +
                                person.getCrationDate() + ";" +
                                (person.getHeight() == null ? "" : person.getHeight()) + ";" +
                                (person.getWeight() == null ? "" : person.getWeight()) + ";" +
                                (person.getHairColor() == null ? "" : person.getHairColor()) + ";" +
                                (person.getNationality() == null ? "" : person.getNationality()) + ";" +
                                locationX + ";" +
                                locationY + ";" +
                                locationZ + ";" +
                                locationName;

                writer.write(line + "\n");
            }

            writer.flush();
            ServerLogger.info("Сохранено {} персонажей в {}", persons.size(), filePath);
            return true;

        } catch (IOException e) {
            ServerLogger.error("Ошибка записи: {}", e.getMessage());
            return false;
        }
    }
}