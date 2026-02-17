package map;

import entities.abilities.Spell;
import entities.characters.Character;
import entities.characters.Enemy;
import entities.users.Account;
import exceptions.GenerateMapException;
import exceptions.ImpossibleMoveException;
import exceptions.InvalidCommandException;
import exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static int length;
    private static int width;
    private Character character;
    private Cell currentCell;
    private static Cell aux;
    private Account characterAccount;
    private boolean startNewGame = false;

    private Grid(int length, int width) {
        Grid.length = length;
        Grid.width = width;
        character = null;
        currentCell = null;
    }

    public static Grid generateMap(int length, int width) throws GenerateMapException {
        if(length > 10 || length < 3 || width > 10 || width < 3) {
            throw new GenerateMapException();
        }

        Random random = new Random();

        Grid grid = new Grid(length, width);
        grid = initialMatrix(grid);

        cellPositioning(grid, CellEntityType.PLAYER);

        cellPositioning(grid, CellEntityType.PORTAL);

        int maxSanctuaries = length * width - 6;

        int nrSanctuaries = random.nextInt(maxSanctuaries - 2 + 1) + 2;

        for(int i = 0; i < nrSanctuaries; i++) {
           cellPositioning(grid, CellEntityType.SANCTUARY);
        }

        int maxEnemies = length * width - 2 - nrSanctuaries;

        int nrEnemies = random.nextInt(maxEnemies - 4 + 1) + 4;

        for(int i = 0; i < nrEnemies; i++) {
            cellPositioning(grid, CellEntityType.ENEMY);
        }

        return grid;
    }

    public static Grid hardcodedGrid(Grid grid) throws GenerateMapException {
        grid = generateMap(5, 5);

        cellPositioning(grid, CellEntityType.PLAYER, 0, 0);
        aux = grid.get(0).get(0);
        grid.setCurrentCell(aux);
        cellPositioning(grid, CellEntityType.VOID, 0, 1);
        cellPositioning(grid, CellEntityType.VOID, 0, 2);
        cellPositioning(grid, CellEntityType.SANCTUARY, 0, 3);
        cellPositioning(grid, CellEntityType.VOID, 0, 4);

        cellPositioning(grid, CellEntityType.VOID, 1, 0);
        cellPositioning(grid, CellEntityType.VOID, 1, 1);
        cellPositioning(grid, CellEntityType.VOID, 1, 2);
        cellPositioning(grid, CellEntityType.SANCTUARY, 1, 3);
        cellPositioning(grid, CellEntityType.VOID, 1, 4);

        cellPositioning(grid, CellEntityType.SANCTUARY, 2, 0);
        cellPositioning(grid, CellEntityType.VOID, 2, 1);
        cellPositioning(grid, CellEntityType.VOID, 2, 2);
        cellPositioning(grid, CellEntityType.VOID, 2, 3);
        cellPositioning(grid, CellEntityType.VOID, 2, 4);

        cellPositioning(grid, CellEntityType.VOID, 3, 0);
        cellPositioning(grid, CellEntityType.VOID, 3, 1);
        cellPositioning(grid, CellEntityType.VOID, 3, 2);
        cellPositioning(grid, CellEntityType.VOID, 3, 3);
        cellPositioning(grid, CellEntityType.ENEMY, 3, 4);

        cellPositioning(grid, CellEntityType.VOID, 4, 0);
        cellPositioning(grid, CellEntityType.VOID, 4, 1);
        cellPositioning(grid, CellEntityType.VOID, 4, 2);
        cellPositioning(grid, CellEntityType.SANCTUARY, 4, 3);
        cellPositioning(grid, CellEntityType.PORTAL, 4, 4);

        return grid;
    }

    public static void cellPositioning(Grid mat, CellEntityType type, int x, int y) {
        Random random = new Random();

        Cell cell = mat.get(x).get(y);

        cell.setVisited(false);
        cell.setType(type);
    }

    public static void cellPositioning(Grid mat, CellEntityType type) {
        Random random = new Random();

        while (true) {
            int x = random.nextInt(width);
            int y = random.nextInt(length);

            Cell cell = mat.get(x).get(y);

            if (cell.getType() == CellEntityType.VOID) {
                cell.setType(type);
                if(type == CellEntityType.PLAYER) {
                    aux = cell;
                    mat.setCurrentCell(aux);
                }
                break;
            }
        }
    }

    public static Grid initialMatrix(Grid mat) {
        for (int i = 0; i < width; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                line.add(new Cell(i, j, CellEntityType.VOID, false));
            }
            mat.add(line);
        }
        return mat;
    }

    public static void displayGrid(Grid grid) {
        for (ArrayList<Cell> elem : grid) {
            for (Cell cell : elem) {
                System.out.print(cell.getType() + " ");
            }
            System.out.println();
        }
    }

    public static void displayMatrix(Grid grid) {
        for(ArrayList<Cell> elem : grid) {
            for(Cell cell : elem) {
                if(!cell.isVisited() && !(cell.getType().equals(CellEntityType.PLAYER))) {
                    System.out.print("N ");
                }
                else if(cell.getType().equals(CellEntityType.PLAYER)) {
                    System.out.print("P ");
                }
                else if(cell.getType().equals(CellEntityType.VOID) && cell.isVisited()) {
                    System.out.print("V ");
                }
            }
            System.out.println();
        }
    }


    public boolean goTo (String direction) throws ImpossibleMoveException, InvalidCommandException, InvalidValueException {
        int newX, newY;
        boolean enemyWinned = false;

            switch (direction) {
                case "N" :
                    newX = currentCell.getX() - 1;
                    newY = currentCell.getY();
                    enemyWinned = moveCharacter(newX, newY, direction);
                    break;
                case "S":
                    newX = currentCell.getX() + 1;
                    newY = currentCell.getY();
                    enemyWinned = moveCharacter(newX, newY, direction);
                    break;
                case "W":
                    newX = currentCell.getX();
                    newY = currentCell.getY() - 1;
                    enemyWinned = moveCharacter(newX, newY, direction);
                    break;
                case "E" :
                    newX = currentCell.getX();
                    newY = currentCell.getY() + 1;
                    enemyWinned = moveCharacter(newX, newY, direction);
                    break;
                case "Q" :
                    startNewGame = true;
                    break;
                default:
                    throw new InvalidCommandException("This direction does not exist! Try another direction!");
        }
        return enemyWinned;
    }

    public boolean moveCharacter(int newX, int newY, String direction) throws ImpossibleMoveException, InvalidCommandException, InvalidValueException {
        if(currentCell.getX() == 0 && direction.equals("N")) {
            throw new ImpossibleMoveException("You can't move north!");
        }
        else if(currentCell.getX() == (width - 1) && direction.equals("S")) {
            throw new ImpossibleMoveException("You can't move south!");
        }
        else if(currentCell.getY() == 0 && direction.equals("W")) {
            throw new ImpossibleMoveException("You can't move west!");
        }
        else if(currentCell.getY() == (length - 1) && direction.equals("E")) {
            throw new ImpossibleMoveException("You can't move east!");
        }
        else {
            boolean check = checkNewCellTypeAndMove(newX, newY);
            return check;
        }
    }

    public boolean checkNewCellTypeAndMove(int newX, int newY) throws InvalidCommandException, InvalidValueException {
        boolean ok = true;

        Cell newCell = this.get(newX).get(newY);

        switch (newCell.getType()) {
            case CellEntityType.VOID:
                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited(true);

                currentCell = this.get(newX).get(newY);
                currentCell.setType(CellEntityType.PLAYER);
                System.out.println("You have reached the void cell! No special event!");
                break;
            case CellEntityType.SANCTUARY:
                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited(true);

                currentCell = this.get(newX).get(newY);
                currentCell.setType(CellEntityType.PLAYER);

                Random random = new Random();

                int newHealt = random.nextInt(10, 31);
                int newMana = random.nextInt(30, 51);

                character.regenerateHealth(newHealt);
                character.regenerateMana(newMana);

                System.out.println("You have reached the cell with the sanctuary! Your life and mana will recharge!");
                System.out.println("New current healt:" + character.getCurrentHealth() +
                        ", New current mana:" + character.getCurrentMana());
                break;
            case CellEntityType.PORTAL:
                int newScore = character.getCurrentLevel() * 5;

                character.setCurrentExperience(character.getCurrentExperience() + newScore);

                character.setCurrentLevel(character.getCurrentLevel() + 1);

                character.getMyAccount().setGamesNumber(character.getMyAccount().getGamesNumber() + 1);

                System.out.println("The old attributes: " + "strength = " + character.getStrength() + ", dexterity = " +
                        character.getDexterity() + ", charisma = " + character.getCharisma());

                character.setCharisma(character.getCharisma()+ new Random().nextInt(1, 6));
                character.setDexterity(character.getDexterity()+ new Random().nextInt(1, 6));
                character.setStrength(character.getStrength()+ new Random().nextInt(1, 6));

                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited(true);

                currentCell = this.get(newX).get(newY);
                currentCell.setType(CellEntityType.PLAYER);


                System.out.println("Number of games played: " + character.getMyAccount().getGamesNumber());
                System.out.println("My new level: " + character.getCurrentLevel());

                startNewGame = true;

                System.out.println("The new attributes: " + "strength = " + character.getStrength() + ", dexterity = " +
                        character.getDexterity() + ", charisma = " + character.getCharisma());
                System.out.println("You have reached the cell with the portal! You will advance to the next level!");

                break;
            case CellEntityType.ENEMY:
                System.out.println("You have reached the cell with the enemy! The battle will begin!");

                boolean fightBool = fight();

                if(!fightBool) {
                    startNewGame = true;
                    ok = false;
                }

                currentCell.setType(CellEntityType.VOID);
                currentCell.setVisited(true);

                currentCell = this.get(newX).get(newY);
                currentCell.setType(CellEntityType.PLAYER);
                break;
        }
        return ok;
    }

    public boolean changeDirection() throws InvalidValueException, InvalidCommandException{
        Scanner scanner = new Scanner(System.in);

        boolean ok = true;

        while(ok) {
            if(startNewGame) {
                break;
            }
            System.out.println("Enter direction: N- North, S - South, W - West, E - East, Q - Quit");

            String direction = scanner.nextLine();

            System.out.println("The player moves " + direction);

            try {
                ok = goTo(direction);
                displayMatrix(this);
            } catch(ImpossibleMoveException | InvalidCommandException e) {
                System.out.println(e.getMessage());
                changeDirection();
            }
        }
        return startNewGame;
    }

    public static int getLength() {
        return length;
    }

    public static void setLength(int length) {
        Grid.length = length;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Grid.width = width;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public static Cell getAux() {
        return aux;
    }

    public static void setAux(Cell aux) {
        Grid.aux = aux;
    }

    public Account getCharacterAccount() {
        return characterAccount;
    }

    public void setCharacterAccount(Account characterAccount) {
        this.characterAccount = characterAccount;
    }

    public boolean isStartNewGame() {
        return startNewGame;
    }

    public void setStartNewGame(boolean startNewGame) {
        this.startNewGame = startNewGame;
    }

    public String myAttack() {
        String myOption;
        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Valid options: A - Normal attack, B - Use ability");
            myOption = scanner.nextLine();

            if (myOption.equals("A") || myOption.equals("B")) {
                break;
            }
            else {
                System.out.println("Choose another type of attack, this one does not exist!");
            }
        }
        return myOption;
    }

    public Spell chooseMyAbility() {
        int idx;
        Spell myAbility = null;
        Scanner scanner1 = new Scanner(System.in);

        while(true) {
            System.out.println("My abilities: ");
            for (int i = 0; i < character.getAbilities().size(); i++) {
                System.out.println(i + " - " + character.getAbilities().get(i));
            }

            try {
                idx = scanner1.nextInt();

                if (idx >= character.getAbilities().size() || idx < 0) {
                    System.out.println("Choose another ability, this one is not in the list");
                } else {
                    myAbility = character.getAbilities().get(idx);
                    break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Choose another ability, this one is not in the list!");
                scanner1.nextLine();
            }
        }

        return myAbility;
    }

    public boolean fight() throws InvalidCommandException, InvalidValueException {
        Enemy enemy = new Enemy();

        while(character.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0) {
            String myOption;

            myOption = myAttack();

            Spell myAbility = null;
            int idx;

            if(!character.getAbilities().isEmpty() && myOption.equals("B")) {
                myAbility = chooseMyAbility();
            }

            if(myOption.equals("B")) {
                if(myAbility == null || character.getCurrentMana() < myAbility.getManaCost()) {
                    System.out.println("You cannot use attack with ability! You resort to normal attack!");
                    myOption = "A";
                }
            }

            if(myOption.equals("A")) {
                System.out.println("The enemy before attack:" + enemy.getCurrentHealth());
                enemy.receiveDamage(character.getDamage());
                System.out.println("The enemy after attack:" + enemy.getCurrentHealth());
            }
            else if(myOption.equals("B")) {
                System.out.println("The character attacks with " + myAbility);
                System.out.println("The enemy before attack:" + enemy.getCurrentHealth());
                character.attackWithAbility(myAbility, enemy);
                System.out.println("The enemy after attack:" + enemy.getCurrentHealth());
                System.out.println("Current mana for character: " + character.getCurrentMana());
            }
            else {
                throw new InvalidCommandException("Invalid option! Choose from available attack types!");
            }

            Spell enemyAbility = null;

            if(!enemy.getAbilities().isEmpty()) {
                int idx2 = new Random().nextInt(0, enemy.getAbilities().size());
                enemyAbility = enemy.getAbilities().get(idx2);
            }

            if(enemy.getCurrentHealth() > 0) {
                Random random = new Random();
                Boolean bool = random.nextBoolean();

                String enemyOption = "";

                if(bool) {
                    enemyOption = "A";
                }
                else {
                    if(enemyAbility == null || enemy.getCurrentMana() < enemyAbility.getManaCost()) {
                        enemyOption = "A";
                        System.out.println("The enemy cannot use attack with ability! The enemy resorts to normal attack!");
                    }
                    else {
                        enemyOption = "B";
                    }

                }

                System.out.println("**************************");
                System.out.println("The choice of enemy: " + enemyOption);

                if(enemyOption.equals("A")) {
                    System.out.println("The character before attack:" + character.getCurrentHealth());
                    character.receiveDamage(enemy.getDamage());
                    System.out.println("The character after attack:" + character.getCurrentHealth());
                }
                else if(enemyOption.equals("B")) {
                    System.out.println("The enemy attacks with " + enemyAbility);
                    System.out.println("The character before attack:" + character.getCurrentHealth());
                    enemy.attackWithAbility(enemyAbility, character);
                    System.out.println("The character after attack:" + character.getCurrentHealth());
                    System.out.println("Current mana for enemy: " + enemy.getCurrentMana());
                }
                else {
                    throw new InvalidCommandException("Invalid option! Choose from available attack types!");
                }
            }
        }

        if(character.getCurrentHealth() > enemy.getCurrentHealth()) {
            System.out.println("The enemy has defeated! Continue the game!");

            character.setAbilities(character.generateAbilities());

            character.setCurrentHealth(character.getCurrentHealth() * 2);
            character.setCurrentMana(character.getMaxMana());

            System.out.println("My old experience: " + character.getCurrentExperience());
            character.setCurrentExperience(character.getCurrentExperience() + new Random().nextInt(1, 10));
            System.out.println("My new experience: " + character.getCurrentExperience());

            return true;
        }
        else {
            System.out.println("The enemy has won!");
            System.out.println("GAME OVER!");
            return false;
        }

    }
}
