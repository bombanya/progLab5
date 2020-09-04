package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Info extends Command{

    public Info(CollectionManager manager){
        super(manager);
        commandName = "info";
    }

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
