package please.help.commands;

import please.help.*;

import java.util.Collections;
import java.util.LinkedList;

public class Add_if_max extends Command{

    public Add_if_max(CollectionManager manager){
        super(manager);
        commandName = "add_if_max";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1){
            System.out.println("Неверно введена комманда.");
            return false;
        }
        Organization org;
        if (manager.managerMode == Mode.CONSOLE) org = Add.buildFromConsole(null);
        else org = Add.buildFromScript(null, data);
        if (org != null) {
            if (manager.collection.size() == 0) manager.collection.add(org);
            else if (org.compareTo(Collections.max(manager.collection)) > 0) manager.collection.add(org);
            else OrganizationBuilder.deleteId(org.getId());
            return true;
        }
        else return manager.managerMode != Mode.SCRIPT;
    }
}
