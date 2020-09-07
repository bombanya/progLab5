package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды update.
 * Формат комманды: update id
 */
public class Update extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Update(CollectionManager manager){
        super(manager);
        commandName = "update";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Ищет в коллекции элемент с введенным id, если находит, то с помощью методов
     * {@link Add#buildFromConsole(Organization)} и
     * {@link Add#buildFromScript(Organization, LinkedList, CollectionManager)} обновляет его.
     * При этом пользователь может прервать обновление, и тогда объект останется без изменений.
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
                    OrganizationBuilder.deleteId(o.getId());
                    Organization org;
                    if (manager.getManagerMode() == Mode.CONSOLE) org = Add.buildFromConsole(o);
                    else org = Add.buildFromScript(o, data, manager);
                    if (org != null){
                        manager.collection.remove(o);
                        manager.collection.add(org);
                    }
                    else {
                        OrganizationBuilder.addId(o.getId());
                        System.out.println("Элемент не был изменен.");
                    }
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
