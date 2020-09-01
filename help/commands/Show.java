package please.help.commands;

import please.help.*;

public class Show extends Command{

    public Show(CollectionManager manager){
        super(manager);
        commandName = "show";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        for (Organization o : manager.collection){
            System.out.println(o);
        }
    }
}
