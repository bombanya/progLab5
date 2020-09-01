package please.help.commands;

import please.help.*;

public abstract class Command {

    protected CollectionManager manager;
    protected String commandName;

    public Command(CollectionManager manager){
        this.manager = manager;
    }

    public String getCommandName() {
        return commandName;
    }

    public abstract void execute(String[] data) throws WrongDataException;
}
