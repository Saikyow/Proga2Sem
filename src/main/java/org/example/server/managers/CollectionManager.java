package org.example.server.managers;


import org.example.packet.element.Coordinates;
import org.example.packet.element.Location;
import org.example.packet.element.Person;
import org.example.packet.enums.Color;
import org.example.packet.enums.Country;
import org.example.server.logger.ServerLogger;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class CollectionManager {


    private HashMap<Long, Person> collection;
    private ZonedDateTime dateTime;

    public CollectionManager() {
        this.collection = new HashMap<>();
        this.dateTime = ZonedDateTime.now();
    }

    public void addCollection(Long key, Person person){
        this.collection.put(key, person);
    }
    public void remove(Long key){
        this.collection.remove(key);
    }


    public void clearCollection(){
        this.collection.clear();
    }

    public HashMap<Long, Person> getCollection() {
        return this.collection;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public void addAllCollection(List<String[]> collectionImportCSV){
        long error_count = 0;
        ServerLogger.info("Загрузка данных в коллекцию");
        long maxID  = 0;
        for(String[] values : collectionImportCSV){
            try{
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
                Person person = new Person(id, name, coordinates, zonedDateTime, height, weight, hairColor, nationality, location);
                if (id > maxID){
                    maxID = id;
                }
                ManagerGenerateId.setId(maxID);


                this.collection.put(key, person);
            }catch (Exception e){
                throw new IllegalArgumentException("Ошибка парсинга " + e.getMessage());
            }
        }
    }



}