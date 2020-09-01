package please.help.commands;

import please.help.*;

public class History extends Command{

    public History(CollectionManager manager){
        super(manager);
        commandName = "history";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        manager.history.printCommands();
    }
}
