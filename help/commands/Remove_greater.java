package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Remove_greater extends Command{

    public Remove_greater(CollectionManager manager){
        super(manager);
        commandName = "remove_greater";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        Organization org;
        if (manager.managerMode == Mode.CONSOLE) org = Add.buildFromConsole(null);
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
