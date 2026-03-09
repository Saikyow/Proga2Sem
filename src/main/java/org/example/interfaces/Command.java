package org.example.interfaces;

/**
 * Базовый интерфейс для всех команд приложения.
 */
public interface Command extends Cloneable{

    /**
     * Возвращает строковое представление команды с описанием.
     *
     * @return строка с названием и описанием команды
     */
    public String toString();

    /**
     * Выполняет команду с заданными аргументами.
     * Реализация по умолчанию ничего не делает.
     *
     * @param args массив аргументов команды
     */
    default void executeCommand(String[] args) {}
}