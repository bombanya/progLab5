package please.help.commands;

import please.help.*;

public class Help extends Command{

    public Help(CollectionManager manager){
        super(manager);
        commandName = "help";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        System.out.println("Список доступных команд:\n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о " + "" +
                "коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся " +
                "команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если " +
                "его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "history : вывести последние 5 команд (без их аргументов)\n" +
                "average_of_annual_turnover : вывести среднее значение поля annualTurnover" +
                " для всех элементов коллекции\n" +
                "count_by_annual_turnover annualTurnover : вывести количество элементов, " +
                "значение поля annualTurnover которых равно заданному\n" +
                "print_field_ascending_type : вывести значения поля type всех элементов в порядке возрастания");
    }
}