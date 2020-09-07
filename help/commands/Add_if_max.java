package please.help.commands;

import please.help.*;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Класс для комманды add_if_max.
 * Формат комманды: add_if_max
 */

public class Add_if_max extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Add_if_max(CollectionManager manager){
        super(manager);
        commandName = "add_if_max";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Создает новый объект типа {@link Organization} полностью аналогично комманде
     * {@link Add} и добавляет его в коллекцию, если он наибольший (сортировка по полю annualTurnover).
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1){
            System.out.println("Неверно введена комманда.");
            return false;
        }
        Organization org;
        if (manager.getManagerMode() == Mode.CONSOLE) org = Add.buildFromConsole(null);
        else org = Add.buildFromScript(null, data, manager);
        if (org != null) {
            if (manager.collection.size() == 0) manager.collection.add(org);
            else if (org.compareTo(Collections.max(manager.collection)) > 0) manager.collection.add(org);
            else OrganizationBuilder.deleteId(org.getId());
        }
        return true;
    }
}
