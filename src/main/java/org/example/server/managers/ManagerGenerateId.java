package org.example.server.managers;

/**
 * Генератор уникальных идентификаторов для объектов Person.
 * Используется синхронизированный доступ для потокобезопасности.
 */
public class ManagerGenerateId {
    private static long id = 0;

    /**
     * Генерирует следующий уникальный идентификатор.
     *
     * @return новый уникальный ID
     */
    public static synchronized long generateId() {
        return ++id;
    }

    /**
     * Устанавливает максимальный ID, если переданное значение больше текущего.
     *
     * @param maxId максимальный ID из загруженной коллекции
     */
    public static synchronized void setId(long maxId) {
        if (maxId > id) {
            id = maxId;
        }
    }
}