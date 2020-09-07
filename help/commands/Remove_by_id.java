package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды remove_by_id.
 * Формат комманды: remove_by_id id
 */
public class Remove_by_id extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Remove_by_id(CollectionManager manager){
        super(manager);
        commandName = "remove_by_id";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Удаляет элемент из коллекции по введенному id, либо выыводит информацию, что такого элемента не существует.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.peek().length != 2) {
            System.out.println("Неверно введена комманда.");
            data.poll();
            return false;
        }

        String[] polledCommand = data.poll();
        try {
            Long id = Long.parseLong(polledCommand[1]);
            for (Organization o : manager.collection){
                if (o.getId().equals(id)){
                    manager.collection.remove(o);
                    OrganizationBuilder.deleteId(id);
                    return true;
                }
            }
            System.out.println("Нет элемента с таким id");
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Комманда должна вводиться вместе со значением типа long.");
            return false;
        }
    }
}
