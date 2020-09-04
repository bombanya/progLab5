package please.help.commands;

import please.help.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Add extends Command{

    public Add(CollectionManager manager){
        super(manager);
        commandName = "add";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        if (manager.managerMode == Mode.CONSOLE){
            Organization org = buildFromConsole(null);
            if (org != null) manager.collection.add(org);
        }
        else{
            Organization org = buildFromScript(null, data);
            if (org != null) manager.collection.add(org);
            else return false;
        }
        return true;
    }

    public static Organization buildFromConsole(Organization org){
        String[] invitations = new String[]{"название организации (тип поля: String, не может быть null)"
                , "координату X (тип поля: int, максимальное значение: 765)"
                , "координату Y (тип поля: int, максимальное значение: 450)"
                , "значение годового оборота (тип поля: double, значение должно быть больше 0)"
                , "тип организации (тип поля: enum, поле не может быть null)"
                , "название улицы (тип поля: String, может быть null)"
                , "индекс (тип поля: String, длина строки должна быть не меньше 4, может быть null)"};
        int iterator = 0;
        Scanner scan = new Scanner(System.in);
        Long id = null;
        LocalDateTime creationDate = null;

        if (org != null) {
            id = org.getId();
            creationDate = org.getCreationDate();
        }

        OrganizationBuilder builder = OrganizationBuilder.createBuilder(id, creationDate);
        if (builder == null){
            System.out.println("Произошло что-то очень плохое.");
            return null;
        }

        while (iterator != invitations.length) {
            System.out.print("Введите " + invitations[iterator]);
            if (iterator == 4) System.out.print("(Возможные варианты:"
                    + Arrays.toString(OrganizationType.values()) + ")");
            if (org != null) System.out.print("(Старое значение поля: " + org.getAllFields()[iterator] + ")");
            System.out.print(": ");

            String input = scan.nextLine();

            if (input.trim().split("\\s+").length == 1 && input.trim().split("\\s+")[0].equals("stopInit")){
                OrganizationBuilder.deleteId(builder.getId());
                return null;
            }

            if (builder.addField(input)) {
                iterator++;
            } else {

                System.out.println("Некорректный ввод. Повторите попытку."
                        + "(Для прерывания инициализации введите 'stopInit')");
            }
        }

        return builder.getOrganization();
    }

    public static Organization buildFromScript(Organization org, LinkedList<String[]> data){
        int iterator = 0;
        Long id = null;
        LocalDateTime creationDate = null;

        if (org != null) {
            id = org.getId();
            creationDate = org.getCreationDate();
        }

        OrganizationBuilder builder = OrganizationBuilder.createBuilder(id, creationDate);
        if (builder == null){
            System.out.println("Произошло что-то очень плохое.");
            return null;
        }

        while (data.size() != 0){
            String[] input = data.poll();
            System.out.println(Arrays.toString(input));

            if (input.length != 1) {
                System.out.println("Ошибка: поля нужно вводить по одному значению в строку.");
                return null;
            }
            else{
                if (builder.addField(input[0])) iterator++;
                else return null;
            }
            if (iterator == 7) return builder.getOrganization();
        }
        return null;
    }
}
