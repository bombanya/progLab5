package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class History extends Command{

    public History(CollectionManager manager){
        super(manager);
        commandName = "history";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        manager.history.printCommands();
        return true;
    }
}
