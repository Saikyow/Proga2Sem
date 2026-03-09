package org.example.User;

import org.example.enums.Country;
import org.example.managers.ManagerGenerateId;
import org.example.enums.Color;

import java.time.ZonedDateTime;

/**
 * Класс, представляющий человека (основной элемент коллекции).
 * Реализует Comparable для сортировки по ID.
 */
public class Person implements Comparable<Person> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Float height;
    private Float weight;
    private Color hairColor;
    private Country nationality;
    private Location location;
    /**
     * Создает нового Person с автоматической генерацией ID и текущей датой.
     *
     * @param name         имя (не может быть пустым)
     * @param coordinates  координаты (не null)
     * @param height       рост (>0 или null)
     * @param weight       вес (>0 или null)
     * @param hairColor    цвет волос (может быть null)
     * @param nationality  национальность (не null)
     * @param location     локация (может быть null)
     */
    public Person(String name, Coordinates coordinates, Float height, Float weight, Color hairColor,
                  Country nationality, Location location) {
        this(ManagerGenerateId.generateId(),
                name,
                coordinates,
                ZonedDateTime.now(),
                height, weight, hairColor, nationality, location);
    }

    /**
     * Создает Person с заданными параметрами (используется при загрузке из файла).
     *
     * @param id           идентификатор
     * @param name         имя
     * @param coordinates  координаты
     * @param creationDate дата создания
     * @param height       рост
     * @param weight       вес
     * @param hairColor    цвет волос
     * @param nationality  национальность
     * @param location     локация
     * @throws IllegalArgumentException при нарушении ограничений
     */
    public Person(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Float height, Float weight, Color hairColor,
                  Country nationality, Location location) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Имя не может быть пустым");
        if (coordinates == null)
            throw new IllegalArgumentException("Координаты не могут быть null");
        if (nationality == null)
            throw new IllegalArgumentException("Национальность не может быть null");
        if (height != null && height <= 0)
            throw new IllegalArgumentException("Рост должен быть > 0");
        if (weight != null && weight <= 0)
            throw new IllegalArgumentException("Вес должен быть > 0");

        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    @Override
    public int compareTo(Person other) {
        return Long.compare(this.id, other.id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCrationDate() {
        return creationDate;
    }

    public Float getHeight() {
        return height;
    }

    public Float getWeight() {
        return weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person [id= " + id + ", name= " + name + ", Height= " + height + ", Weight= " + weight +
                ", HairColor= " + hairColor + ", nationality= " + nationality + "]";
    }
}