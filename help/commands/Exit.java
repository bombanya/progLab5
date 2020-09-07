package please.help.commands;

import please.help.CollectionManager;
import please.help.Mode;

import java.util.LinkedList;

/**
 * Класс для комманды exit.
 * Формат комманды: exit
 */
public class Exit extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Exit(CollectionManager manager){
        super(manager);
        commandName = "exit";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Устанавливает режим работы {@link CollectionManager} равным {@link Mode#EXIT},
     * что приводит к завершению работы программы.
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
        manager.setManagerMode(Mode.EXIT);
        return true;
    }
}
