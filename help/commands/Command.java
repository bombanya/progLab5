package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public abstract class Command {

    protected CollectionManager manager;
    protected String commandName;

    public Command(CollectionManager manager){
        this.manager = manager;
    }

    public String getCommandName() {
        return commandName;
    }

    public abstract boolean execute(LinkedList<String[]> data);
}
