package please.help.commands;

import please.help.*;

public class Remove_by_id extends Command{

    public Remove_by_id(CollectionManager manager){
        super(manager);
        commandName = "remove_by_id";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length != 2) throw new WrongDataException();
        try {
            int id = Integer.parseInt(data[1]);
            for (Organization o : manager.collection){
                if (o.getId() == id){
                    manager.collection.remove(o);
                    return;
                }
            }
            System.out.println("Нет элемента с таким id");
        }
        catch (NumberFormatException e){
            throw new WrongDataException();
        }
    }
}
