package please.help.commands;

import please.help.*;

public class Average_of_annual_turnover extends Command{

    public Average_of_annual_turnover(CollectionManager manager){
        super(manager);
        commandName = "average_of_annual_turnover";
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        double sum = 0;
        for (Organization o : manager.collection){
            sum += o.getAnnualTurnover();
        }
        System.out.println("Среднее значение поля annualTurnover для всех элементов коллекции: "
                + sum/manager.collection.size());
    }
}
