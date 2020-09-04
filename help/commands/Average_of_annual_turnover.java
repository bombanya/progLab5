package please.help.commands;

import please.help.*;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Average_of_annual_turnover extends Command{

    public Average_of_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "average_of_annual_turnover";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        double average = manager.collection.stream()
                .collect(Collectors.averagingDouble(Organization::getAnnualTurnover));
        System.out.println("Среднее значение поля annualTurnover для всех элементов коллекции: " + average);
        return true;
    }
}
