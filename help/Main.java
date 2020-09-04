package please.help;

public class Main {

    public static void main(String[] args){
        CollectionManager manager = CollectionManager.createManager(args);
        if (manager != null) manager.start();
    }
}
