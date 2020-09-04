package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Show extends Command{

    public Show(CollectionManager manager){
        super(manager);
        commandName = "show";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        manager.collection.forEach(System.out::println);
        return true;
    }
}
