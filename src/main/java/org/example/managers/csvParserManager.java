package org.example.managers;

import org.example.User.Coordinates;
import org.example.User.Location;
import org.example.User.Person;
import org.example.enums.Color;
import org.example.enums.Country;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Парсер для загрузки коллекции из CSV-файла.
 */
public class csvParserManager {

    /**
     * Загружает коллекцию объектов Person из CSV-файла.
     *
     * @param fileName имя файла для загрузки
     * @return HashMap с загруженными объектами
     */
    public static HashMap<Long, Person> LoadFromCSV(String fileName) {
        HashMap<Long, Person> collections = new HashMap<>();
        File file = new File(fileName);

        if (!file.exists()) {
            managerInputOutput.writeLineIO("Ошибка! Файл " + fileName + " не найден.\n");
            return collections;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                managerInputOutput.writeLineIO("Файл пустой! \n");
                return collections;
            }
            managerInputOutput.writeLineIO("Заголовок: " + headerLine + "\n");

            String Line;
            int lineNumber = 0;
            long maxID = 0;

            while ((Line = reader.readLine()) != null) {
                lineNumber++;
                Line = Line.trim();

                if (Line.isEmpty()) {
                    continue;
                }
                try {
                    Person person = parseFromCSV(Line);
                    if (person != null) {
                        String[] values = Line.split(";", -1);
                        Long key = Long.parseLong(values[0]);

                        collections.put(key, person);

                        if (person.getId() > maxID) {
                            maxID = person.getId();
                        }
                    }
                } catch (Exception e) {
                    managerInputOutput.writeLineIO("Ошибка в строке " + lineNumber + e.getMessage() + "\n");
                }
            }
            ManagerGenerateId.setId(maxID);
            managerInputOutput.writeLineIO("Коллекция загружена! \n");

        } catch (FileNotFoundException e) {
            managerInputOutput.writeLineIO("Ошибка! " + e.getMessage() + "\n");
        } catch (IOException e) {
            managerInputOutput.writeLineIO("Ошибка! " + e.getMessage() + "\n");
        }
        return collections;
    }

    /**
     * Парсит одну строку CSV и создает объект Person.
     *
     * @param Line строка CSV
     * @return объект Person или null при ошибке
     * @throws IllegalArgumentException при ошибке парсинга
     */
    private static Person parseFromCSV(String Line) {
        String[] values = Line.split(";", -1);

        try {
            long key = Long.parseLong(values[0]);
            long id = Long.parseLong(values[1]);
            String name = values[2];

            if (name.isEmpty()) throw new IllegalArgumentException("Имя не может быть пустым. \n");

            Double x = Double.parseDouble(values[3]);
            Long y = Long.parseLong(values[4]);
            Coordinates coordinates = new Coordinates(x, y);

            ZonedDateTime zonedDateTime = ZonedDateTime.parse(values[5], DateTimeFormatter.ISO_ZONED_DATE_TIME);

            Float height = values[6].isEmpty() ? null : Float.parseFloat(values[6]);

            if (height != null && height <= 0) {
                throw new IllegalArgumentException("Рост должен быть > 0. \n");
            }

            Float weight = values[7].isEmpty() ? null : Float.parseFloat(values[7]);

            if (weight != null && weight <= 0) {
                throw new IllegalArgumentException("Вес должен быть > 0. \n");
            }

            Color hairColor = values[8].isEmpty() ? null : Color.valueOf(values[8]);

            Country nationality = values[9].isEmpty() ? null : Country.valueOf(values[9]);

            Location location = null;
            if (!values[10].isEmpty() && !values[11].isEmpty()
                    && !values[12].isEmpty() && !values[13].isEmpty()) {
                Double LocationX = Double.parseDouble(values[10]);
                int LocationY = Integer.parseInt(values[11]);
                Double LocationZ = Double.parseDouble(values[12]);
                String LocName = values[13];
                location = new Location(LocationX, LocationY, LocationZ, LocName);
            }
            return new Person(id, name, coordinates, zonedDateTime, height, weight, hairColor, nationality, location);

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга " + e.getMessage() + "\n");
        }
    }
}