package please.help.commands;

import please.help.*;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Класс для комманды average_of_annual_turnover.
 * Формат комманды: average_of_annual_turnover
 */
public class Average_of_annual_turnover extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Average_of_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "average_of_annual_turnover";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Выводит среднее значение поля annualTurnover для всех элементов коллекции.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
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
