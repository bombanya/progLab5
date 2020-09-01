package please.help.commands;

import please.help.*;

public class Add extends Command{

    public Add(CollectionManager manager){
        super(manager);
        commandName = "add";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        Organization element = ElementBuilder.build(null);
        if (element != null) manager.collection.add(element);
    }
}
