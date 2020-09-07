package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды remove_greater.
 * Формат комманды: remove_greater
 */
public class Remove_greater extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Remove_greater(CollectionManager manager){
        super(manager);
        commandName = "remove_greater";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Создает объект, аналогично комманде add, а затем удаляет из коллекции все
     * элементы, превышающие его (сортировка по полю annualTurnover).
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
        Organization org;
        if (manager.getManagerMode() == Mode.CONSOLE) org = Add.buildFromConsole(null);
        else org = Add.buildFromScript(null, data, manager);
        if (org != null) {
            Organization[] toDelete = manager.collection.stream().filter(p -> p.compareTo(org) > 0)
                    .toArray(Organization[]::new);
            for (Organization o : toDelete){
                OrganizationBuilder.deleteId(o.getId());
                manager.collection.remove(o);
            }
        }
        return true;
    }
}
