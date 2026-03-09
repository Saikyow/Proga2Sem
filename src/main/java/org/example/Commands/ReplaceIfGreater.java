package org.example.Commands;

import org.example.User.Person;
import org.example.User.PersonAsker;
import org.example.interfaces.Command;
import org.example.managers.CollectionManager;

import static org.example.Runner.Runner.managerInputOutput;

/**
 * Команда replace_if_greater - заменяет значение по ключу, если новое значение больше старого.
 * Сравнение производится по полю weight.
 */
public class ReplaceIfGreater implements Command {
    private PersonAsker personAsker;
    private CollectionManager collectionManager;

    /**
     * Создает команду replace_if_greater.
     *
     * @param collectionManager менеджер коллекции
     * @param personAsker объект для создания нового Person
     */
    public ReplaceIfGreater(CollectionManager collectionManager, PersonAsker personAsker){
        this.collectionManager = collectionManager;
        this.personAsker = personAsker;
    }

    /**
     * Проверяет аргументы команды.
     *
     * @param args массив аргументов (должен содержать ключ)
     * @return true, если аргумент корректен
     */
    public boolean checkArg(String[] args) {
        if (args == null || args.length == 0 || args[0].isEmpty()) {
            managerInputOutput.writeLineIO("Не указан ключ \n");
            return false;
        }
        try {
            Long.parseLong(args[0]);
            return true;
        } catch (NumberFormatException e) {
            managerInputOutput.writeLineIO("Ключ должен быть целым числом. Вы ввели: " + args[0]);
            return false;
        }
    }

    /**
     * Выполняет команду replace_if_greater.
     * Сравнивает вес нового и старого элемента. Если новый вес больше, производит замену.
     *
     * @param args аргументы команды (ключ)
     */
    public void executeCommand(String[] args){
        if(!checkArg(args)){
            return;
        }

        try{
            Long key = Long.parseLong(args[0]);
            if (!collectionManager.getCollections().containsKey(key)){
                managerInputOutput.writeLineIO("Нету элемента с таким ключём \n");
                return;
            }
            Person oldPerson = collectionManager.getCollections().get(key);
            managerInputOutput.writeLineIO("Текущий элемент коллекции с ключём " + key + "\n");
            managerInputOutput.writeLineIO("Ключ: " + key + " name: "+ oldPerson.getName() +
                    " Вес: " + oldPerson.getWeight() +  " id: " + oldPerson.getId() + " " + oldPerson.getCrationDate() + "\n");

            Person newPerson = personAsker.createPerson();

            if (comparePerson(newPerson, oldPerson)){
                collectionManager.Insert(key, newPerson);
                managerInputOutput.writeLineIO("Новый элемент больше старого => производится замена. \n");
            } else {
                managerInputOutput.writeLineIO("Новый элемент меньше старого => замена не производится. \n");
            }

        } catch (NumberFormatException e){
            managerInputOutput.writeLineIO("Ошибка при обработке ключа " + e.getMessage());
        }
    }

    /**
     * Сравнивает двух Person по полю weight.
     *
     * @param newPerson новый объект Person
     * @param oldPerson старый объект Person
     * @return true, если вес нового больше веса старого
     */
    public boolean comparePerson(Person newPerson, Person oldPerson){
        if (newPerson.getWeight() != null && oldPerson.getWeight() != null){
            return newPerson.getWeight() > oldPerson.getWeight();
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "replace_if_greater - заменяет значение по ключу, если новое значение больше старого";
    }
}