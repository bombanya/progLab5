package please.help;

import please.help.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class CollectionManager {

    public LinkedList<Organization> collection = new LinkedList<>();
    private LocalDateTime creationDate;
    public CommandsHistory history = new CommandsHistory();

    private final ArrayList<Command> listOfCommands = new ArrayList<>();
    private final Scanner scan = new Scanner(System.in);

    {
        listOfCommands.add(new Add(this));
        listOfCommands.add(new Help(this));
        listOfCommands.add(new Info(this));
        listOfCommands.add(new Show(this));
        listOfCommands.add(new Update(this));
        listOfCommands.add(new Remove_by_id(this));
        listOfCommands.add(new Clear(this));
        listOfCommands.add(new Save(this));
        listOfCommands.add(new Execute_script(this));
        listOfCommands.add(new History(this));
        listOfCommands.add(new Add_if_max(this));
        listOfCommands.add(new Remove_greater(this));
        listOfCommands.add(new Average_of_annual_turnover(this));
        listOfCommands.add(new Count_by_annual_turnover(this));
        listOfCommands.add(new Print_field_ascending_type(this));
    }

    public CollectionManager(String[] args){
        try {
            collection = JSONParser.parse(args, this);
            System.out.println("Коллекция успешно загружена.");
        }
        catch (WrongDataException e) {
            System.out.println("Не удалось загрузить коллекцию из файла.");
            creationDate = LocalDateTime.now();
        }
    }

    public void setCreationDate(LocalDateTime date){
        creationDate = date;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public void start(){
        System.out.println("Вывести справку по доступным командам: 'help'.\n-----");
        String[] parsedInput = scan.nextLine().trim().split("\\s+");

        while (!(parsedInput.length == 1 && parsedInput[0].equals("exit"))){
            try {
                executeCommand(parsedInput);
            }
            catch (WrongDataException e){
                System.out.println("Некорректный ввод. Повторите попытку.\n-----");
            }
            finally {
                parsedInput = scan.nextLine().trim().split("\\s+");
            }
        }
    }

    public void executeCommand(String[] parsedInput) throws WrongDataException{
        for (Command c : listOfCommands) {
            if (parsedInput[0].equals(c.getCommandName())) {
                c.execute(parsedInput);
                history.addCommand(c);
                System.out.println("-----");
                return;
            }
        }
        System.out.println("Некорректный ввод. Повторите попытку.\n-----");
    }
}
