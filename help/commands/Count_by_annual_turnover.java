package please.help.commands;

import please.help.*;

import java.util.LinkedList;

public class Count_by_annual_turnover extends Command{

    public Count_by_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "count_by_annual_turnover";
    }

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
