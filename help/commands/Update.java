package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Update extends Command{

    public Update(CollectionManager manager){
        super(manager);
        commandName = "update";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length != 2) throw new WrongDataException();
        try {
            LinkedList<Organization> backup = new LinkedList<>();
            for (Organization o : manager.collection) backup.add(o.clone());

            int id = Integer.parseInt(data[1]);
            for (Organization o : manager.collection){
                if (o.getId() == id){
                    Organization result = ElementBuilder.build(o);
                    if (result == null) {
                        manager.collection = backup;
                        System.out.println("Элемент не был изменен.");
                    }
                    return;
                }
            }
            System.out.println("Нет элемента с таким id");
        }
        catch (NumberFormatException | CloneNotSupportedException e){
            throw new WrongDataException();
        }
    }
}
