package please.help.commands;

import org.json.JSONObject;
import please.help.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Save extends Command{

    public Save(CollectionManager manager){
        super(manager);
        commandName = "save";
    }

    private JSONObject objForLocalDateTime(LocalDateTime date){
        JSONObject objForDate = new JSONObject();
        objForDate.put("year", date.getYear()).put("month", date.getMonthValue());
        objForDate.put("day", date.getDayOfMonth()).put("hour", date.getHour());
        objForDate.put("minute", date.getMinute()).put("second", date.getSecond());
        return objForDate;
    }

    @Override
    public void execute(String[] data) throws WrongDataException {
        if (data.length > 1) throw new WrongDataException();
        JSONObject json = new JSONObject();

        LocalDateTime creationDateOfCollection = manager.getCreationDate();
        json.put("creationDate", objForLocalDateTime(creationDateOfCollection));

        for (Organization org : manager.collection){
            JSONObject objForElement = new JSONObject();
            objForElement.put("name", org.getName());

            JSONObject objForCoord = new JSONObject();
            objForCoord.put("x", org.getCoordinates().getX()).put("y", org.getCoordinates().getY());
            objForElement.put("coordinates", objForCoord);

            LocalDateTime date = org.getCreationDate();
            objForElement.put("creationDate", objForLocalDateTime(date));

            objForElement.put("annualTurnover", org.getAnnualTurnover());
            objForElement.put("type", org.getType().toString());


            JSONObject objForAddress = new JSONObject();
            objForAddress.put("street", org.getOfficialAddress().getStreet());
            objForAddress.put("zipCode", org.getOfficialAddress().getZipCode());
            objForElement.put("officialAddress", objForAddress);

            json.put(String.valueOf(org.getId()), objForElement);
        }

        try(FileOutputStream writer = new FileOutputStream("organizations.json")){
            byte[] buffer = json.toString().getBytes();
            writer.write(buffer, 0, buffer.length);
        }
        /*
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        catch (IOException e) {
            System.out.println("Во время сохранения произошла ошибка.");
        }
    }
}
