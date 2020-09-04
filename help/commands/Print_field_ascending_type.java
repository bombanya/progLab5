package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Print_field_ascending_type extends Command{

    public Print_field_ascending_type(CollectionManager manager){
        super(manager);
        commandName = "print_field_ascending_type";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }

        manager.collection.stream().map(Organization::getType).sorted().forEach(System.out::println);
        return true;
    }
}
