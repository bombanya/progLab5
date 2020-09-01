package please.help.commands;

import please.help.*;

public class Count_by_annual_turnover extends Command{

    public Count_by_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "count_by_annual_turnover";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length != 2) throw new WrongDataException();

        try{
            double value = Double.parseDouble(data[1]);
            int counter = 0;
            for (Organization o : manager.collection){
                if (o.getAnnualTurnover() == value) counter++;
            }
            System.out.println("У " + counter + " элементов значение поля annualTurnover равно " + value + ".");
        }
        catch (IllegalArgumentException e){
            throw new WrongDataException();
        }

    }
}
