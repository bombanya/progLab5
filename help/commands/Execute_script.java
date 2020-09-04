package please.help.commands;

import please.help.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Execute_script extends Command{

    public Execute_script(CollectionManager manager){
        super(manager);
        commandName = "execute_script";
    }

    @Override
    public boolean execute(LinkedList<String[]> data) {
        if (data.size() == 0 || data.peek().length != 2) {
            System.out.println("Неверно введена комманда.");
            data.poll();
            return false;
        }
        LinkedList<Organization> backup = new LinkedList<>();
        String[] polledCommand = data.poll();
        File scriptFile = new File(polledCommand[1]);

        if (!scriptFile.isFile()){
            System.out.println("Файл скрипта не обнаружен.");
            return true;
        }

        try {
            for (Organization org : manager.collection) {
                backup.add(org.clone());
            }
        }
        catch (CloneNotSupportedException e){
            System.out.println("R.I.P.");
        }

        String[] parsedLines;

        try(FileReader reader = new FileReader(scriptFile)){
            char[] buff = new char[100];
            StringBuilder builder = new StringBuilder();
            int c;
            while((c = reader.read(buff)) > 0){
                if (c < 100){
                    buff = Arrays.copyOf(buff, c);
                }
                builder.append(String.valueOf(buff));
            }
            parsedLines = builder.toString().split("\\n");
        }
        catch (IOException e) {
            System.out.println("Ошибка во время чтения файла.");
            return true;
        }

        LinkedList<String[]> parsedCommands = new LinkedList<>();
        for (String str : parsedLines) parsedCommands.add(str.trim().split("\\s+"));
        manager.managerMode = Mode.SCRIPT;

        while (parsedCommands.size() != 0){
            String[] stopRecursion = parsedCommands.peek();
            System.out.println(Arrays.toString(stopRecursion));
            if (stopRecursion.length == 2 && stopRecursion[0].equals("execute_script")
                    && stopRecursion[1].equals(polledCommand[1])){
                System.out.println("Попытка запустить рекурсию. Продолжить выполнение скрипта? (y/n)");
                Scanner scan = new Scanner(System.in);
                while (true){
                    String[] input = scan.nextLine().trim().split("\\s+");
                    if (input.length == 1){
                        if (input[0].equals("y")) break;
                        if (input[0].equals("n")) {
                            manager.managerMode = Mode.CONSOLE;
                            return true;
                        }
                    }
                    System.out.println("Некорректный ввод. Повторите попытку.");
                }
            }
            if (!(manager.executeCommand(parsedCommands))){
                manager.collection = backup;
                System.out.println("Не удалось выполнить скрипт. Все изменения в коллекции отменены.");
                manager.managerMode = Mode.CONSOLE;
                return true;
            }
        }
        manager.managerMode = Mode.CONSOLE;
        return true;
    }
}
