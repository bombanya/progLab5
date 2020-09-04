package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Clear extends Command{

    public Clear(CollectionManager manager){
        super(manager);
        commandName = "clear";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        manager.collection.clear();
        return true;
    }
}
