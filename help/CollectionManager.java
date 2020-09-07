package please.help;

import please.help.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * Класс для управления коллекцией объектов типа {@link Organization}.
 * Хранит все необходимые для коллекции служебные данные и список доступных комманд.
 */

public class CollectionManager {

    /** Коллекция объектов типа {@link Organization}*/
    public LinkedList<Organization> collection = new LinkedList<>();
    private LocalDateTime creationDate = LocalDateTime.now();
    private final CommandsHistory history = new CommandsHistory();
    private Mode managerMode = Mode.CONSOLE;
    private String saveFile = null;
    private int currentScriptSkip = 0;
    private String currentScript;

    private final ArrayList<Command> listOfCommands = new ArrayList<>();


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

    private CollectionManager() {}

    /**
     * Создает новый объект типа {@link OrganizationBuilder}.
     * При вызове пробует загрузить коллекцию из файла. В случае успеха возвращает новый объект.
     * В случае неудачи предлагает пользователю создать новую пустую коллекцию.
     * @param args путь к файлу с сохраненной коллекцией
     * @return {@link CollectionManager} - если коллекция успешно загрузилась из файла
     * или если пользователь согласился создать новую, null - иначе
     */
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

    /**
     * Запускает процесс считывания комманд из консоли.
     */
    public void start(){
        System.out.println("Вывести справку по доступным командам: 'help'.\n-----");
        LinkedList<String[]> inputList = new LinkedList<>();
        Scanner scan = new Scanner(System.in);

        while (managerMode != Mode.EXIT){
            inputList.add(scan.nextLine().trim().split("\\s+"));
            executeCommand(inputList);
        }
    }

    /**
     * Исполняет очередную комманду, либо выводит сообщение об ошибке.
     * @param inputList список введенных данных (при консольном вводе содержит один массив
     *                  из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *                  каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
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

    /**
     * Устанавливает значение поля creationDate.
     * @param date новое дата и время создания коллекции
     */
    public void setCreationDate(LocalDateTime date){
        creationDate = date;
    }

    /**
     * Возвращает значение поля creationDate
     * @return дата и время создания коллекции
     */
    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    /**
     * Устанавливает значение поля saveFile.
     * @param saveFile путь к файлу, из которого загружена коллекция
     */
    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    /**
     * Возвращает значение поля saveFile.
     * @return путь к файлу, из которого загружена коллекция
     */
    public String getSaveFile() {
        return saveFile;
    }

    /**
     * Устанавливает значение поля managerMode.
     * @param managerMode режим работы {@link CollectionManager}
     * @see Mode
     */
    public void setManagerMode(Mode managerMode) {
        this.managerMode = managerMode;
    }

    /**
     * Возвращает значение поля managerMode.
     * @return режим работы {@link CollectionManager}
     * @see Mode
     */
    public Mode getManagerMode() {
        return managerMode;
    }

    /**
     * Устанавливает значение поля currentScriptSkip.
     * @param currentScriptSkip количество строк, которые будут пропущены при загрузке скрипта.
     *                          Нужно для возможности изменять файл скрипта, не прерывая его выполнение.
     */
    public void setCurrentScriptSkip(int currentScriptSkip) {
        this.currentScriptSkip = currentScriptSkip;
    }

    /**
     * Возвращает значение поля currentScriptSkip.
     * @return количество строк, которые будут пропущены при загрузке скрипта.
     */
    public int getCurrentScriptSkip() {
        return currentScriptSkip;
    }

    /**
     * Увеличение значения поля currentScriptSkip на 1.
     */
    public void incrementScriptSkip(){
        currentScriptSkip++;
    }

    /**
     * Устанавливает значение поля currentScript.
     * @param currentScript путь к файлу, из которого загружен скрипт.
     *                      Нужно для возможности изменять файл скрипта, не прерывая его выполнение.
     */
    public void setCurrentScript(String currentScript) {
        this.currentScript = currentScript;
    }

    /**
     * Возвращает значение поля currentScript.
     * @return путь к файлу, из которого загружен скрипт.
     */
    public String getCurrentScript() {
        return currentScript;
    }

    /**
     * Выводит последние пять успешно выполненных комманд без аргументов.
     */
    public void printHistory(){
        history.printCommands();
    }
}
