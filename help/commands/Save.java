package please.help.commands;

import org.json.JSONObject;
import please.help.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Save extends Command{

    public Save(CollectionManager manager){
        super(manager);
        commandName = "save";
    }

    private JSONObject objForLocalDateTime(LocalDateTime date){
        return new JSONObject().put("year", date.getYear()).put("month", date.getMonthValue())
                .put("day", date.getDayOfMonth()).put("hour", date.getHour())
                .put("minute", date.getMinute()).put("second", date.getSecond());
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
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
            if (org.getOfficialAddress().getStreet() == null) objForAddress.put("street", JSONObject.NULL);
            else objForAddress.put("street", org.getOfficialAddress().getStreet());
            if (org.getOfficialAddress().getZipCode() == null) objForAddress.put("zipCode", JSONObject.NULL);
            else objForAddress.put("zipCode", org.getOfficialAddress().getZipCode());
            objForElement.put("officialAddress", objForAddress);

            json.put(org.getId().toString(), objForElement);
        }

        File saveFile;
        if (manager.saveFile != null) {
            saveFile = new File(manager.saveFile);
            if (!saveFile.isFile()){
                System.out.println("Файл сохранения не обнаружен.");
                return true;
            }
        }
        else{
            int i = 0;
            saveFile = new File("organizations.json");
            while (saveFile.isFile()){
                i++;
                saveFile = new File("organizations" + i + ".json");
            }
            System.out.println("Будет создан новый файл сохранения " + saveFile.getName());
        }

        try(FileOutputStream writer = new FileOutputStream(saveFile)){
            byte[] buffer = json.toString().getBytes();
            writer.write(buffer, 0, buffer.length);
            return true;
        }
        catch (IOException e) {
            System.out.println("Во время сохранения произошла ошибка.");
            return false;
        }
    }
}
