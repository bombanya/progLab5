package please.help.commands;

import please.help.*;

import java.util.LinkedList;


/**
 * Класс для комманды info.
 * Формат комманды: info
 */
public class Info extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Info(CollectionManager manager){
        super(manager);
        commandName = "info";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Выводит информацию о коллекции: тип, дата инициализации, количество элементов.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        System.out.println("Информация о коллекции:\n" +
                "Тип: LinkedList<Organization>\n" +
                "Дата инициализации: " + manager.getCreationDate() +
                "\nКоличество элементов: " + manager.collection.size());
        return true;
    }
}
