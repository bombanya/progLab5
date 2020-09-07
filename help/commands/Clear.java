package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды clear.
 * Формат комманды: clear
 */
public class Clear extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Clear(CollectionManager manager){
        super(manager);
        commandName = "clear";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Очищает коллекцию.
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
        manager.collection.clear();
        return true;
    }
}
