package please.help;

import please.help.commands.Command;

import java.util.ArrayList;

public class CommandsHistory {

    private final ArrayList<String> history = new ArrayList<>();

    public void addCommand(Command c){
        history.add(c.getCommandName());
        if (history.size() > 5) history.remove(0);
    }

    public void printCommands(){
        for (String s : history){
            System.out.println(s);
        }
    }
}
