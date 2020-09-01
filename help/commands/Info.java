package please.help.commands;

import please.help.*;

public class Info extends Command{

    public Info(CollectionManager manager){
        super(manager);
        commandName = "info";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        System.out.println("Информация о коллекции:\n" +
                "Тип: LinkedList<Organization>\n" +
                "Дата инициализации: " + manager.getCreationDate() +
                "\nКоличество элементов: " + manager.collection.size());
    }
}
