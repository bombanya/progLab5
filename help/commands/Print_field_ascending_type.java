package please.help.commands;

import please.help.*;

import java.util.ArrayList;
import java.util.Collections;

public class Print_field_ascending_type extends Command{

    public Print_field_ascending_type(CollectionManager manager){
        super(manager);
        commandName = "print_field_ascending_type";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();

        ArrayList<OrganizationType> types = new ArrayList<>();
        for (Organization o : manager.collection){
            OrganizationType type = o.getType();
            types.add(type);
        }

        Collections.sort(types);
        for (OrganizationType type : types){
            System.out.println(type);
        }
    }
}
