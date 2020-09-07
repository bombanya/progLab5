package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Класс для комманды count_by_annual_turnover.
 * Формат комманды: count_by_annual_turnover annualTurnover
 */
public class Count_by_annual_turnover extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Count_by_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "count_by_annual_turnover";
    }

    /**
     * Метод для основной функциоальности комманды.
     * Выводит количество элементов, значение поля annualTurnover которых равно заданному.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.peek().length != 2) {
            System.out.println("Неверно введена комманда.");
            data.poll();
            return false;
        }
        String[] polledCommand = data.poll();

        try{
            double value = Double.parseDouble(polledCommand[1]);
            long counter = manager.collection.stream().filter(p -> p.getAnnualTurnover() == value).count();
            System.out.println("У " + counter + " элементов значение поля annualTurnover равно " + value + ".");
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Комманда должна вводиться вместе со значением типа double.");
            return false;
        }

    }
}
