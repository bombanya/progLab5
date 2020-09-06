package please.help.commands;

import please.help.CollectionManager;
import please.help.Mode;

import java.util.LinkedList;

public class Exit extends Command{

    public Exit(CollectionManager manager){
        super(manager);
        commandName = "exit";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        manager.managerMode = Mode.EXIT;
        return true;
    }
}
