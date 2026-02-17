package exceptions;

public class GenerateMapException extends Exception {
    public GenerateMapException() {
        super("the length or width exceeds the allowed limit");
    }
}
