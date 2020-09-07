package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды print_field_ascending_type.
 * Формат комманды: print_field_ascending_type
 */
public class Print_field_ascending_type extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Print_field_ascending_type(CollectionManager manager){
        super(manager);
        commandName = "print_field_ascending_type";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Выводит значения поля type всех элементов в порядке возрастания.
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

        manager.collection.stream().map(Organization::getType).sorted().forEach(System.out::println);
        return true;
    }
}
