package please.help.commands;

import please.help.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Execute_script extends Command{

    public Execute_script(CollectionManager manager){
        super(manager);
        commandName = "execute_script";
    }

    private void add(String[] fields) throws WrongDataException{
        Organization org = createOrg(fields, null);
        manager.collection.add(org);
        manager.history.addCommand(new Add(null));
    }

    private void add_if_max(String[] fields) throws WrongDataException{
        Organization org = createOrg(fields, null);
        if (manager.collection.size() == 0) manager.collection.add(org);
        else if (org.compareTo(Collections.max(manager.collection)) > 0) manager.collection.add(org);
        manager.history.addCommand(new Add_if_max(null));
    }

    private void remove_greater(String[] fields) throws WrongDataException{
        Organization org = createOrg(fields, null);
        manager.collection.removeIf(e -> e.compareTo(org) > 0);
        manager.history.addCommand(new Remove_greater(null));
    }

    private void update(String[] fields, int id) throws WrongDataException{
        for (Organization o : manager.collection){
            if (o.getId() == id){
                createOrg(fields, o);
                return;
            }
        }
        System.out.println("Нет элемента с таким id");
    }

    private Organization createOrg(String[] fields, Organization org) throws WrongDataException{
        Organization newOrg = org;
        if (org == null) newOrg = new Organization();
        String[] readyFields = new String[7];

        for (int i = 0; i < 7; i++){
            if (fields[i].trim().split("\\s+").length > 1) throw new WrongDataException();
            readyFields[i] = fields[i].trim().split("\\s+")[0];
            if (readyFields[i].equals("")) readyFields[i] = null;
            System.out.println(readyFields[i]);
        }

        try {
            newOrg.setName(readyFields[0]);
            newOrg.setCoordinateX(Integer.parseInt(readyFields[1]));
            newOrg.setCoordinateY(Integer.parseInt(readyFields[2]));
            newOrg.setAnnualTurnover(Double.parseDouble(readyFields[3]));
            newOrg.setType(OrganizationType.valueOf(readyFields[4]));
            newOrg.setOfficialAddressStreet(readyFields[5]);
            newOrg.setOfficialAddressZipCode(readyFields[6]);

            return newOrg;
        }
        catch (NullPointerException | IllegalArgumentException e){
            throw new WrongDataException();
        }
    }

    @Override
    public void execute(String[] data) {
        LinkedList<Organization> backup = new LinkedList<>();

        try {
            for (Organization org : manager.collection) {
                backup.add(org.clone());
            }
        }
        catch (CloneNotSupportedException e){
            System.out.println("R.I.P.");
        }

        try {
            if (data.length != 2) throw new WrongDataException();

            String unparsedCommands;
            try (FileReader reader = new FileReader(data[1])) {
                char[] buff = new char[100];
                StringBuilder builder = new StringBuilder();
                int c;
                while ((c = reader.read(buff)) > 0) {
                    if (c < 256) {
                        buff = Arrays.copyOf(buff, c);
                    }
                    builder.append(String.valueOf(buff));
                }
                unparsedCommands = builder.toString();
            }
            /*catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

            String[] parsedCommands = unparsedCommands.split("\\n");


            for (int i = 0; i < parsedCommands.length; i++) {
                String[] readyCommand = parsedCommands[i].trim().split("\\s+");
                System.out.println(Arrays.toString(readyCommand));
                if ((readyCommand.length == 1 &&
                        (readyCommand[0].equals("add")
                                || readyCommand[0].equals("add_if_max") || readyCommand[0].equals("remove_greater"))) ||
                        (readyCommand.length == 2 && readyCommand[0].equals("update"))) {
                    if ((parsedCommands.length - i - 1) >= 7) {
                        String[] fields = Arrays.copyOfRange(parsedCommands, i + 1, i + 8);
                        i += 7;
                        switch (readyCommand[0]) {
                            case "update":
                                update(fields, Integer.parseInt(readyCommand[1]));
                                break;
                            case "add":
                                add(fields);
                                break;
                            case "add_if_max":
                                add_if_max(fields);
                                break;
                            default:
                                remove_greater(fields);
                                break;
                        }
                        System.out.println("-----");

                    } else throw new WrongDataException();
                }
                else manager.executeCommand(parsedCommands[i].trim().split("\\s+"));
            }
        }
        catch (IOException | NumberFormatException e){
            manager.collection = backup;
            System.out.println("Не удалось выполнить скрипт. Все изменения в коллекции отменены.");
        }
    }
}
