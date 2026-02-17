import entities.characters.Character;
import entities.users.Account;
import entities.users.Credentials;
import exceptions.*;
import input.JsonInput;
import map.Grid;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Account> accounts;
    private Grid grid;
    private ArrayList<Character> availableCharacters = new ArrayList<>();
    private Account currentAccount;

    public Game() {
        JsonInput input = new JsonInput();
        accounts = JsonInput.deserializeAccounts();
    }

    public void displayAccounts() {
        for (Account elem : accounts) {
            System.out.println(elem);
        }
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;

        System.out.println("Login with your e-mail and password: ");

        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        System.out.println("Enter your password: ");
        String passwd = scanner.nextLine();

        Credentials inputCred = new Credentials(email, passwd);

        for(Account elem : accounts) {
            if(elem.getInformation().getCredentials().equals(inputCred)) {
                ok = true;
                currentAccount = elem;
                break;
            }
        }
        if(ok) {
            System.out.println("Login was done successfully!");

            availableCharacters = returnCharacters(inputCred);
        }
        else {
            System.out.println("Credentials are incorrect!");
        }
         return ok;
    }

    public ArrayList<Character> returnCharacters (Credentials credentials) {
        ArrayList<Character> characters = new ArrayList<>();

        for(Account elem : accounts) {
            if(elem.getInformation().getCredentials().equals(credentials)) {
                characters = elem.getCharacters();
                break;
            }
        }
        return characters;
    }

    public Character chooseCharacter() throws IndexOutOfBoundsException {
        Scanner scanner = new Scanner(System.in);
        String input;

        while(true) {
            int option = 0;
            System.out.println("Choose the character: ");

            for(Character elem : availableCharacters) {
                System.out.println(option + " - " + elem.getCharacterName());
                option++;
            }
            input = scanner.nextLine();

            try {
                if (Integer.parseInt(input) <= option - 1 && Integer.parseInt(input) >= 0) {
                    break;
                }
                else {
                    System.out.println("Invalid choice!");
                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice!");
            }
        }
        return availableCharacters.get(Integer.parseInt(input));
    }

    public String chooseGridPopulation(Grid grid) throws InvalidCommandException{
        Scanner scanner = new Scanner(System.in);
        String val;

        while(true) {
            System.out.println("Grid generation options: 0 - Random generation, 1 - Hardcoded generation");

            val = scanner.next();

            if(val.equals("0") || val.equals("1")) {
                break;
            }
        }
        return val;
    }

    public void reset(int length, int width) throws InvalidValueException, ImpossibleMoveException, GenerateMapException, InvalidCommandException {
        if(chooseGridPopulation(grid).equals("0")) {
            grid = Grid.generateMap(length, width);
        }
        else {
            grid = Grid.hardcodedGrid(grid);
        }

        grid.setCharacter(chooseCharacter());
        grid.getCharacter().setMyAccount(currentAccount);

        grid.getCharacter().setMaxMana(new Random().nextInt(70, 100));
        grid.getCharacter().setMaxHealth(new Random().nextInt(100, 121 ));

        grid.getCharacter().setAbilities();

        System.out.println("The chosen character:");
        System.out.println(grid.getCharacter());
        Grid.displayMatrix(grid);
    }

    public void run(int length, int width) throws InvalidValueException, GenerateMapException, NumberFormatException, InvalidCommandException {
        int count = 0;
        int maxLoginAttempts = 3;
        boolean loginSuccessful = false;

        while(count < maxLoginAttempts) {
            if(!login()) {
                count++;
            }
            else {
                loginSuccessful = true;
                count = 3;
            }
        }
        if(loginSuccessful) {
            if(chooseGridPopulation(grid).equals("0")) {
                grid = Grid.generateMap(length, width);
            }
            else {
                grid = Grid.hardcodedGrid(grid);
            }

            grid.setCharacter(chooseCharacter());
            grid.getCharacter().setMyAccount(currentAccount);

            grid.getCharacter().setMaxMana(new Random().nextInt(70, 101));
            grid.getCharacter().setMaxHealth(new Random().nextInt(100, 121 ));

            grid.getCharacter().setAbilities();

            System.out.println("The chosen character:");
            System.out.println(grid.getCharacter());
            Grid.displayMatrix(grid);
            boolean gridStatus = grid.changeDirection();

            try {
                while(gridStatus) {
                    grid.setStartNewGame(false);
                    reset(length, width);
                    gridStatus = grid.changeDirection();
                }
            } catch (ImpossibleMoveException | InvalidCommandException e) {
                System.out.println(e.getMessage());
                grid.changeDirection();
            }
        }
    }

}
