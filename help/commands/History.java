package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды history.
 * Формат комманды: history
 */
public class History extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public History(CollectionManager manager){
        super(manager);
        commandName = "history";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Выводит последние 5 команд без их аргументов.
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
        manager.printHistory();
        return true;
    }
}
