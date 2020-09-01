package please.help.commands;

import please.help.*;

public class Remove_greater extends Command{

    public Remove_greater(CollectionManager manager){
        super(manager);
        commandName = "remove_greater";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        Organization element = ElementBuilder.build(null);
        if (element != null) manager.collection.removeIf(e -> e.compareTo(element) > 0);
    }
}
