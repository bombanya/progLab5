package please.help;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class OrganizationBuilder {

    private final Long id;
    private final LocalDateTime creationDate;
    private final static ArrayList<Long> idList = new ArrayList<>(Arrays.asList(0L));

    private String name;
    private int x;
    private int y;
    private double annualTurnover;
    private OrganizationType type;
    private String street;
    private String zipCode;
    private int iterator = 0;

    private OrganizationBuilder(Long id, LocalDateTime creationDate){
        this.id = id;
        this.creationDate = creationDate;
    }

    public static void deleteId(Long id){
        idList.remove(id);
    }

    public static void addId(Long id){
        idList.add(id);
    }

    public Long getId() {
        return id;
    }

    public static void backup(LinkedList<Organization> list){
        idList.clear();
        list.forEach(p -> OrganizationBuilder.addId(p.getId()));
    }

    public static OrganizationBuilder createBuilder(Long id, LocalDateTime creationDate){
        if (id != null){
            if (id <= 0 || idList.contains(id)){
                System.out.println("Ошибка: элемент с таким id уже существует.");
                return null;
            }
            else{
                idList.add(id);
                return new OrganizationBuilder(id, creationDate);
            }
        }
        else{
            id = Collections.min(idList) + 1;
            while (idList.contains(id)){
                if (id.equals(Long.MAX_VALUE)){
                    System.out.println("Ошибка: коллекция полностью заполнена.");
                }
                else id++;
            }
            idList.add(id);
            return new OrganizationBuilder(id, LocalDateTime.now());
        }
    }

    public boolean addField(String unparsedField){
        try {
            if (unparsedField.trim().split("\\s+").length != 1) {
                System.out.println("Ошибка: поля нужно вводить по одному значению в строку.");
                return false;
            } else {
                String parsedField = unparsedField.trim().split("\\s+")[0];
                if (parsedField.equals("")) parsedField = null;

                if (parsedField == null && iterator < 5) {
                    System.out.println("Ошибка: поле не может быть пусто.");
                    return false;
                }

                if (iterator == 0) name = parsedField;

                else if (iterator == 1) {
                    int x = Integer.parseInt(parsedField);
                    if (x > 765) {
                        System.out.println("Ошибка: максимальное значение поля: 765");
                        return false;
                    }
                    else this.x = x;
                }

                else if (iterator == 2){
                    int y = Integer.parseInt(parsedField);
                    if (y > 450) {
                        System.out.println("Ошибка: максимальное значение поля: 450");
                        return false;
                    }
                    else this.y = y;
                }

                else if (iterator == 3){
                    double annualTurnover = Double.parseDouble(parsedField);
                    if (annualTurnover <= 0){
                        System.out.println("Ошибка: значение поля должно быть больше 0");
                        return false;
                    }
                    else this.annualTurnover = annualTurnover;
                }

                else if (iterator == 4) type = OrganizationType.valueOf(parsedField);
                else if (iterator == 5) street = parsedField;
                else if (iterator == 6){
                    if (parsedField != null && parsedField.length() < 4){
                        System.out.println("Ошибка: длина строки должна быть не меньше 4");
                        return false;
                    }
                    else zipCode = parsedField;
                }

                else{
                    System.out.println("Ошибка: значения всех полей уже введены");
                    return false;
                }

                iterator++;
                return true;
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Ошибка: значение не соответствует типу поля.");
            return false;
        }
    }

    public Organization getOrganization(){
        if (iterator < 6){
            return null;
        }
        else{
            Address officialAddress = new Address(street, zipCode);
            Coordinates coordinates = new Coordinates(x, y);
            return new Organization(id, creationDate, name, coordinates, annualTurnover, type, officialAddress);
        }
    }
}
