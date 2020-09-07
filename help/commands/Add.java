package please.help.commands;

import please.help.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс для комманды add.
 * Формат комманды: add
 */
public class Add extends Command{

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Add(CollectionManager manager){
        super(manager);
        commandName = "add";
    }

    /**
     * Метод для основной функциоальности комманды. Запускает процесс создания нового
     * объекта типа {@link Organization} с помощью методов {@link Add#buildFromConsole(Organization)}
     * и {@link Add#buildFromScript(Organization, LinkedList, CollectionManager)} и добавляет его в коллекцию.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return false;
        }
        Organization org;
        if (manager.getManagerMode() == Mode.CONSOLE) org = buildFromConsole(null);
        else org = buildFromScript(null, data, manager);
        if (org != null) manager.collection.add(org);
        return true;
    }

    /**
     * Создает объект из данных с консоли.
     * Выводит подсказки для пользователя, считывает введенные им данные и с помошью
     * {@link OrganizationBuilder} создает новый объект типа {@link Organization}.
     * @param org объект, который нужно обновить. Если нужно создать новый, то необходимо передать null
     * @return {@link Organization}, если пользователь не прервал создание объекта, null - в ином случае
     */
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

    /**
     * Создает объект из данных из файла.
     * Использует данные из файла для того, чтобы с помощью {@link OrganizationBuilder} создать новый объект
     * типа {@link Organization}. Если во время работы метода встречаются некорректные данные, то метод
     * предлагает пользователю следующие варианты действий:
     * <p>- можно исправить некорректную строку в файле и продолжить выполнение;</p>
     * <p>- можно пропустить некорректную строку и продолжить выполение;</p>
     * <p>- можно прервать добавление нового объекта.</p>
     * @param org объект, который нужно обновить. Если нужно создать новый, то необходимо передать null
     * @param data список введенных данных из файла (содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @param manager объект типа {@link CollectionManager}, передается из вызывающей комманды
     * @return {@link Organization}, если объект успешно создан, null - в ином случае
     */
    public static Organization buildFromScript(Organization org, LinkedList<String[]> data, CollectionManager manager){
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

        while (iterator != 7){
            if (data.size() == 0){
                System.out.println("В файле больше нет данных, но очередной объект создан не полностью.");
            }
            else {
                String[] input = data.poll();
                System.out.println(Arrays.toString(input));

                if (input.length != 1) {
                    System.out.println("Ошибка: поля нужно вводить по одному значению в строку.");
                } else {
                    if (builder.addField(input[0])){
                        iterator++;
                        manager.incrementScriptSkip();
                        continue;
                    }
                }
            }

            System.out.println("Ошибка во время добавления нового объекта. Вы можете:\n" +
                    "- исправить некорректную строку в файле и продолжить выполнение" +
                    "(все предыдущие строки должны остаться без изменений): 'update'\n" +
                    "- пропустить некорректную строку и продолжить выполение: 'skip'\n" +
                    "- прервать добавление нового объекта: 'stopInit'");
            Scanner scan = new Scanner(System.in);
            while (true){
                String[] input = scan.nextLine().trim().split("\\s+");
                if (input.length == 1){
                    if (input[0].equals("update")){
                        if (!(Execute_script.openScript(manager.getCurrentScript(),
                                manager.getCurrentScriptSkip(), data))) return null;
                        else break;
                    }
                    else if (input[0].equals("skip")) {
                        if (data.size() != 0) manager.incrementScriptSkip();
                        break;
                    }

                    else if (input[0].equals("stopInit")){
                        if (data.size() != 0) manager.incrementScriptSkip();
                        return null;
                    }
                }
                System.out.println("Некорректный ввод. Повторите попытку.");
            }
        }
        return builder.getOrganization();
    }
}
