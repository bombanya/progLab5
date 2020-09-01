package please.help;

import java.io.IOException;

public class WrongDataException extends IOException {
    public WrongDataException(){
        super("Attempt to initialize a class with invalid data.");
    }
}
