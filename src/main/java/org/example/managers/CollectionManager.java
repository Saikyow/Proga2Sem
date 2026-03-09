package org.example.managers;

import org.example.User.Person;

import java.util.HashMap;

import static org.example.Runner.Runner.managerInputOutput;
import static org.example.managers.ManagerGenerateId.generateId;

/**
 * Управляет коллекцией объектов Person, обеспечивает загрузку, сохранение и модификацию данных.
 */
public class CollectionManager {
    private HashMap<Long, Person> collections = new HashMap<>();
    private java.time.ZonedDateTime lastInitTime;
    private final String fileName;
    /**
     * Создает менеджер коллекции и загружает данные из файла по умолчанию.
     */
    public CollectionManager(String fileName) {
        this.fileName = fileName;
        this.lastInitTime = java.time.ZonedDateTime.now();
        loadCollectionFromFile();
    }

    /**
     * Загружает коллекцию из CSV-файла.
     * Если файл существует и содержит данные, они парсятся и добавляются в коллекцию.
     */
    public void loadCollectionFromFile() {
        HashMap<Long, Person> loaded = csvParserManager.LoadFromCSV(fileName);
        if (!loaded.isEmpty()) {
            this.collections = loaded;
            managerInputOutput.writeLineIO("Коллекция загружена с размером " + collections.size() + "\n");
        }
    }

    /**
     * Вставляет новый элемент в коллекцию по заданному ключу.
     *
     * @param key    ключ элемента
     * @param person объект Person для вставки
     */
    public void Insert(Long key, Person person) {
        this.collections.put(key, person);
    }

    /**
     * Удаляет элемент из коллекции по ключу.
     *
     * @param key ключ элемента для удаления
     */
    public void remove(Long key) {
        this.collections.remove(key);
    }

    /**
     * Очищает всю коллекцию.
     */
    public void clearCollections() {
        this.collections.clear();
    }
    public String getFileName() {
        return fileName;
    }
    /**
     * Возвращает коллекцию объектов Person.
     *
     * @return HashMap, представляющий коллекцию
     */
    public HashMap<Long, Person> getCollections() {
        return collections;
    }
}