package entities.characters;

import entities.abilities.Spell;
import entities.users.Account;
import exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends Entity {
    private String characterName;
    private int currentExperience;
    private int currentLevel;
    private int strength;
    private int charisma;
    private int dexterity;
    private Account myAccount = new Account();

    public Character(String characterName, int currentExperience, int currentLevel,
                     int strength, int charisma, int dexterity, ArrayList<Spell> abilities,
                     int maxHealth, int maxMana, boolean immuneToFire, boolean immuneToIce,
                     boolean immuneToEarth) {
        super(abilities, maxHealth, maxMana, immuneToFire, immuneToIce, immuneToEarth);
        this.characterName = characterName;
        this.currentExperience = currentExperience;
        this.currentLevel = currentLevel;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
    }

    public Character(String characterName, int currentExperience, int currentLevel) {
        this.characterName = characterName;
        this.currentExperience = currentExperience;
        this.currentLevel = currentLevel;
    }

    public String getCharacterName() {
        return characterName;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getStrength() {
        return strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setAbilities() throws InvalidValueException {
        setAbilities(generateAbilities());
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(Account myAccount) {
        this.myAccount = myAccount;
    }

    @Override
    public String toString() {
//        String obj = super.toString();
//
//        obj += "\n";
        String obj = "";

        obj += " CHARACTER NAME: ";
        obj += characterName;
        obj += "\n";
        obj += "    {Current experience: ";
        obj += currentExperience;
        obj += ", Current level: ";
        obj += currentLevel;
        obj += ", Strength: ";
        obj += strength;
        obj += ", Charisma: ";
        obj += charisma;
        obj += ", Dexterity: ";
        obj += dexterity;
        obj += "}";
        obj += "\n";
        obj += super.toString();
        obj += "\n";

        return obj;
    }
}

