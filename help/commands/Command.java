package please.help.commands;

import please.help.*;

import java.util.LinkedList;

/**
 * Абстрактный класс для всех комманд.
 */

public abstract class Command {

    protected CollectionManager manager;
    protected String commandName;

    /**
     * @param manager объект типа {@link CollectionManager}, из которого вызывается комманда.
     */
    public Command(CollectionManager manager){
        this.manager = manager;
    }

    /**
     * Возвращает название комманды.
     * @return название комманды
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Абстрактный метод для основной функциоальности комманды. Переопределяется всеми комманадми.
     * Все комманды проверяют корректность формата ввода.
     * @param data список введенных данных (при консольном вводе содержит один массив
     *             из всех введенных в строку данных, при исполнении скрипта содержит множество массивов, в
     *             каждом из которых содержатся данные из соответствующей строки)
     * @return true - комманда успешно выполнена, false - в ином случае
     */
    public abstract boolean execute(LinkedList<String[]> data);
}
