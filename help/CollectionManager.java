package please.help;

import please.help.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class CollectionManager {

    public LinkedList<Organization> collection = new LinkedList<>();
    private LocalDateTime creationDate = LocalDateTime.now();
    public CommandsHistory history = new CommandsHistory();
    public Mode managerMode = Mode.CONSOLE;
    public String saveFile = null;
    public int currentScriptSkip = 0;
    public String currentScript;

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
        listOfCommands.add(new Exit(this));
    }

    private CollectionManager(){
    }

    public static CollectionManager createManager(String[] args){
        CollectionManager manager = new CollectionManager();
        boolean success = JSONParser.parse(args, manager);
        if (success){
            System.out.println("Коллекция успешно загружена.");
            return manager;
        }
        else{
            System.out.println("Коллекция не была загружена. Создать новую? (y/n)");
            Scanner scan = new Scanner(System.in);
            while (true){
                String[] input = scan.nextLine().trim().split("\\s+");
                if (input.length == 1){
                    if (input[0].equals("y")) return new CollectionManager();
                    if (input[0].equals("n")) return null;
                }
                System.out.println("Некорректный ввод. Повторите попытку.");
            }
        }
    }

    public void setCreationDate(LocalDateTime date){
        creationDate = date;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public void start(){
        System.out.println("Вывести справку по доступным командам: 'help'.\n-----");
        LinkedList<String[]> inputList = new LinkedList<>();

        while (managerMode != Mode.EXIT){
            inputList.add(scan.nextLine().trim().split("\\s+"));
            executeCommand(inputList);
        }
    }

    public boolean executeCommand(LinkedList<String[]> inputList){
        for (Command c : listOfCommands) {
            if (inputList.peek()[0].equals(c.getCommandName())) {
                if (c.execute(inputList)){
                    history.addCommand(c);
                    if (!(c.getCommandName().equals("execute_script"))) System.out.println("-----");
                    return true;
                }
                else{
                    System.out.println("-----");
                    return false;
                }
            }
        }
        System.out.println("Некорректный ввод. Повторите попытку.\n-----");
        inputList.poll();
        return false;
    }
}
