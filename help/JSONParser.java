package please.help;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class JSONParser {

    private static LocalDateTime parseDate(JSONObject objForDate) throws JSONException, DateTimeException{
        int year = objForDate.getInt("year");
        int month = objForDate.getInt("month");
        int day = objForDate.getInt("day");
        int hour = objForDate.getInt("hour");
        int minute = objForDate.getInt("minute");
        int second = objForDate.getInt("second");
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    public static LinkedList<Organization> parse(String[] args, CollectionManager manager) throws WrongDataException{
        if (args.length != 1) throw new WrongDataException();

        LinkedList<Organization> collection = new LinkedList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        String toParse;

        try(FileReader reader = new FileReader(args[0])){
            char[] buff = new char[100];
            StringBuilder builder = new StringBuilder();
            int c;
            while((c = reader.read(buff)) > 0){
                if (c < 256){
                    buff = Arrays.copyOf(buff, c);
                }
                builder.append(String.valueOf(buff));
            }
            toParse = builder.toString();
        }
        /*catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        catch (IOException e) {
            throw new WrongDataException();
        }

        try{
            JSONObject json = new JSONObject(toParse);
            manager.setCreationDate(parseDate(json.getJSONObject("creationDate")));

            for (String key : JSONObject.getNames(json)){
                if (!key.equals("creationDate")) {
                    int id = Integer.parseInt(key);
                    if (ids.contains(id)) throw new WrongDataException();
                    ids.add(id);

                    JSONObject elementObj = json.getJSONObject(key);

                    LocalDateTime date = parseDate(elementObj.getJSONObject("creationDate"));
                    Organization org = new Organization(id, date);

                    org.setName(elementObj.getString("name"));

                    JSONObject objForCoord = elementObj.getJSONObject("coordinates");
                    org.setCoordinateX(objForCoord.getInt("x"));
                    org.setCoordinateY(objForCoord.getInt("y"));

                    org.setAnnualTurnover(elementObj.getDouble("annualTurnover"));
                    org.setType(OrganizationType.valueOf(elementObj.getString("type")));

                    JSONObject objForAddress = elementObj.getJSONObject("officialAddress");
                    org.setOfficialAddressStreet(objForAddress.getString("street"));
                    org.setOfficialAddressZipCode(objForAddress.getString("zipCode"));

                    collection.add(org);
                }
            }
        }
        catch (IllegalArgumentException | JSONException | DateTimeException e){
            throw new WrongDataException();
        }
        return collection;
    }
}
