import exceptions.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Test {
    public static void main(String[] args) throws InvalidValueException, InvalidCommandException, GenerateMapException {
        Game game = new Game();
        game.run(5, 5);
    }
}