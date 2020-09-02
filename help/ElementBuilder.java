package please.help;

import java.util.Arrays;
import java.util.Scanner;

public class ElementBuilder {

    public static Organization build(Organization org){
        String[] invitations = new String[]{"название организации", "координату X", "координату Y"
                , "значение годового оборота", "тип организации", "название улицы", "индекс"};
        int i = 0;
        Organization element;
        Scanner scan = new Scanner(System.in);

        if (org != null) element = org;
        else element = new Organization();

        while (i != invitations.length){
            System.out.print("Введите " + invitations[i]);
            if (i == 4) System.out.print("(Возможные варианты:" + Arrays.toString(OrganizationType.values()) + ")");
            if (org != null) System.out.print( "(Старое значение поля: " + org.getAllFields()[i] + ")");
            System.out.print(": ");

            String[] parsedInput = scan.nextLine().trim().split("\\s+");

            try {
                if (parsedInput.length > 1) {
                    System.out.println("Некорректный ввод. Повторите попытку."
                            + "(Для прерывания инициализации введите 'stopInit')");
                } else {
                    String readyInput;
                    if (!parsedInput[0].equals("")) {
                        readyInput = parsedInput[0];
                        if (readyInput.equals("stopInit")){
                            return null;
                        }
                    }
                    else readyInput = null;

                    if (i == 0) {
                        element.setName(readyInput);
                    }

                    else if (i == 1){
                        element.setCoordinateX(Integer.parseInt(readyInput));
                    }

                    else if (i == 2){
                        element.setCoordinateY(Integer.parseInt(readyInput));
                    }

                    else if (i == 3){
                        element.setAnnualTurnover(Double.parseDouble(readyInput));
                    }

                    else if (i == 4){
                        element.setType(OrganizationType.valueOf(readyInput));
                    }

                    else if (i == 5){
                        element.setOfficialAddressStreet(readyInput);
                    }

                    else {
                        element.setOfficialAddressZipCode(readyInput);
                    }
                    i++;
                }
            }
            catch (WrongDataException | NullPointerException | IllegalArgumentException e){
                System.out.println("Некорректный ввод. Повторите попытку. " +
                        "(Для прерывания инициализации введите 'stopInit')");
            }
        }
        return element;
    }
}
