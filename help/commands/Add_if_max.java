package please.help.commands;

import please.help.*;

import java.util.Collections;

public class Add_if_max extends Command{

    public Add_if_max(CollectionManager manager){
        super(manager);
        commandName = "add_if_max";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        Organization element = ElementBuilder.build(null);
        if (element != null) {
            if (manager.collection.size() == 0) manager.collection.add(element);
            else if (element.compareTo(Collections.max(manager.collection)) > 0) manager.collection.add(element);
        }
    }
}
