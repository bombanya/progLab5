package please.help.commands;

import please.help.*;
public class Clear extends Command{

    public Clear(CollectionManager manager){
        super(manager);
        commandName = "clear";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        manager.collection.clear();
    }
}
