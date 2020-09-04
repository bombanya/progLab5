package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Remove_by_id extends Command{

    public Remove_by_id(CollectionManager manager){
        super(manager);
        commandName = "remove_by_id";
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
                    manager.collection.remove(o);
                    OrganizationBuilder.deleteId(id);
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
