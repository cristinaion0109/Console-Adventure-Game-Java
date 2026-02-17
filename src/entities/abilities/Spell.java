package entities.abilities;

import exceptions.InvalidValueException;

public abstract class Spell {
    private String nameAbility;
    private int damage;
    private int manaCost;

    public Spell() {

    }

    public Spell(String nameAbility, int damage, int manaCost) throws InvalidValueException {
        this.nameAbility = nameAbility;

        if(damage < 0) {
            throw new InvalidValueException("the damage must be positive");
        }
        this.damage = damage;

        if(manaCost < 0) {
            throw new InvalidValueException("the mana must be positive");
        }
        this.manaCost = manaCost;
    }

    public String getNameAbility() {
        return nameAbility;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setNameAbility(String nameAbility) {
        this.nameAbility = nameAbility;
    }

    public void setDamage(int damage) throws InvalidValueException {
        if(damage < 0) {
            throw new InvalidValueException("the damage must be positive");
        }
        this.damage = damage;
    }

    public void setMana(int manaCost) throws InvalidValueException {
        if(manaCost < 0) {
            throw new InvalidValueException("the mana must be positive");
        }
        this.manaCost = manaCost;
    }

    @Override
    public String toString() {
        return "Ability: " + nameAbility + ", " + "Damage: " +
                damage + ", " + "ManaCost: " + manaCost;
    }
}
