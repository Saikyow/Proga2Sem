package org.example.client.managers;

import org.example.packet.element.*;
import org.example.packet.enums.*;

public class ManagerCreatorPerson {
  private final ManagerInputOutput managerInputOutput = ManagerInputOutput.getInstance();

  public Person createPerson() {
    managerInputOutput.writeLineIO("Создание Person \n");

    String name = askString("Введите имя (не может быть пустым) ", false);
    Coordinates coordinates = askCoordinates();

    Color hairColor = askEnum("Выберите цвет волос ", Color.class, false);
    Country nationality = askEnum("Выберите страну ", Country.class, false);

    Float weight = askNumber("Введите вес (Float) ", false, Float.class);
    Float height = askNumber("Введите рост (Float) ", false, Float.class);

    Location location = askLocation();

    return new Person(name, coordinates, height, weight, hairColor, nationality, location);
  }

  /**
   * Запрашивает строковое значение.
   *
   * @param message сообщение для пользователя
   * @param canBeNull разрешено ли пустое значение
   * @return введенная строка или null
   */
  private String askString(String message, boolean canBeNull) {
    while (true) {
      managerInputOutput.writeLineIO(message);
      String input = managerInputOutput.readLineIO().trim();
      if (input.isEmpty()) {
        managerInputOutput.writeLineIO("Не может быть пустым\n");
        continue;
      }
      return input;
    }
  }

  /**
   * Запрашивает числовое значение указанного типа.
   *
   * @param <T> тип числа
   * @param message сообщение для пользователя
   * @param canBeNull разрешено ли пустое значение
   * @param type класс типа числа
   * @return число указанного типа или null
   */
  private <T extends Number> T askNumber(String message, boolean canBeNull, Class<T> type) {
    while (true) {
      managerInputOutput.writeLineIO(message);
      String input = managerInputOutput.readLineIO().trim().replace(",", ".");
      if (input.isEmpty() && canBeNull) {
        return null;
      }

      try {
        if (type == Float.class) {
          Float val = Float.parseFloat(input);
          if (val <= 0) {
            managerInputOutput.writeLineIO("Ошибка! Число должно быть больше 0 \n");
            continue;
          }
          return type.cast(val);
        }
        if (type == Double.class) {
          return type.cast(Double.parseDouble(input));
        }
        if (type == Integer.class) {
          return type.cast(Integer.parseInt(input));
        }
        if (type == Long.class) {
          return type.cast(Long.parseLong(input));
        }
      } catch (NumberFormatException e) {
        managerInputOutput.writeLineIO("Ошибка! Введите число корректно \n");
      }
    }
  }

  /**
   * Запрашивает координаты у пользователя.
   *
   * @return объект Coordinates
   */
  private Coordinates askCoordinates() {
    managerInputOutput.writeLineIO("Введите координаты\n");
    Double x = askNumber("Введите x (Double) ", false, Double.class);
    Long y = askNumber("Введите y (Long) ", false, Long.class);
    try {
      return new Coordinates(x, y);
    } catch (IllegalArgumentException e) {
      managerInputOutput.writeLineIO("Ошибка" + e.getMessage() + "\n");
      return askCoordinates();
    }
  }

  /**
   * Запрашивает значение перечисления.
   *
   * @param <T> тип перечисления
   * @param message сообщение для пользователя
   * @param enumClass класс перечисления
   * @param canBeNull разрешено ли пустое значение
   * @return значение перечисления или null
   */
  private <T extends Enum<T>> T askEnum(String message, Class<T> enumClass, boolean canBeNull) {
    managerInputOutput.writeLineIO(
        message + " " + java.util.Arrays.toString(enumClass.getEnumConstants()) + ": ");
    String input = managerInputOutput.readLineIO().trim().toUpperCase();
    if (input.isEmpty() && canBeNull) return null;
    try {
      return Enum.valueOf(enumClass, input);
    } catch (Exception e) {
      managerInputOutput.writeLineIO("Ошибка! Выберете значение из списка\n");
    }
    return askEnum(message, enumClass, canBeNull);
  }

  /**
   * Запрашивает локацию у пользователя.
   *
   * @return объект Location или null
   */
  private Location askLocation() {
    while (true) {
      managerInputOutput.writeLineIO("Хотите добавить страну? (y/n): ");
      String answer = managerInputOutput.readLineIO().trim().toLowerCase();

      if (answer.equals("y")) {
        Double x = askNumber("Введите x (Double) ", false, Double.class);
        Integer y = askNumber("Введите y (Integer) ", true, Integer.class);
        Double z = askNumber("Введите z (Double) ", false, Double.class);
        String name = askString("Название локации ", false);
        return new Location(x, y, z, name);
      }

      if (answer.equals("n")) {
        return null;
      }

      managerInputOutput.writeLineIO("Ошибка! Введите 'y' или 'n'\n");
    }
  }
}
