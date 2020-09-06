package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Update extends Command{

    public Update(CollectionManager manager){
        super(manager);
        commandName = "update";
    }

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
                    if (manager.managerMode == Mode.CONSOLE) org = Add.buildFromConsole(o);
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
