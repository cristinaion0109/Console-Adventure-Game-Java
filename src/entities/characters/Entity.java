package entities.characters;

import entities.abilities.Earth;
import entities.abilities.Fire;
import entities.abilities.Ice;
import entities.abilities.Spell;
import entities.fight.Battle;
import exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle {
    private ArrayList<Spell> abilities;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private boolean immuneToFire;
    private boolean immuneToIce;
    private boolean immuneToEarth;

    public Entity() {

    }

    public Entity(ArrayList<Spell> abilities, int maxHealth, int maxMana,
                  boolean immuneToFire, boolean immuneToIce, boolean immuneToEarth) {
        this.abilities = abilities;
        currentHealth = maxHealth;
        this.maxHealth = maxHealth;
        currentMana = maxMana;
        this.maxMana = maxMana;
        this.immuneToFire = immuneToFire;
        this.immuneToIce = immuneToIce;
        this.immuneToEarth = immuneToEarth;
    }

    public Entity(int maxHealth, int maxMana) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.maxMana = maxMana;
        currentMana = maxMana;
    }

    public ArrayList<Spell> getAbilities() {
        return abilities;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean isImmuneToFire() {
        return immuneToFire;
    }

    public boolean isImmuneToIce() {
        return immuneToIce;
    }

    public boolean isImmuneToEarth() {
        return immuneToEarth;
    }

    public void setAbilities(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        currentMana = maxMana;
    }

    public void setImmuneToFire(boolean immuneToFire) {
        this.immuneToFire = immuneToFire;
    }

    public void setImmuneToIce(boolean immuneToIce) {
        this.immuneToIce = immuneToIce;
    }

    public void setImmuneToEarth(boolean immuneToEarth) {
        this.immuneToEarth = immuneToEarth;
    }

    public void regenerateHealth(int value) {
        if(currentHealth + value > maxHealth) {
            currentHealth = maxHealth;
        }
        else {
            currentHealth += value;
        }
    }

    public void regenerateMana(int value) {
        if(currentMana + value > maxMana) {
            currentMana = maxMana;
        }
        else {
            currentMana += value;
        }
    }

    public ArrayList<Spell> generateAbilities() throws InvalidValueException {
        ArrayList<Spell> result = new ArrayList<>();
        Random random = new Random();

        int nrAbilities = random.nextInt(4) + 3;

        result.add(new Ice());
        result.add(new Fire());
        result.add(new Earth());

        int dif = nrAbilities - 3;

        while(dif != 0) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0 :
                    result.add(new Ice());
                    break;
                case 1:
                    result.add(new Fire());
                    break;
                case 2:
                    result.add(new Earth());
                    break;

                default:
                    break;
            }
            dif--;
        }
        return result;
    }

    public boolean immunity(Spell spell) {
        boolean immune = (spell.getNameAbility().equals("Ice") && immuneToIce) ||
                (spell.getNameAbility().equals("Fire") && immuneToFire)
                || (spell.getNameAbility().equals("Earth") && immuneToEarth);

        return immune;
    }

    public void attackWithAbility(Spell ability, Entity enemy) {
        this.setCurrentMana(this.getCurrentMana() - ability.getManaCost());

        if (!enemy.immunity(ability)) {
            enemy.receiveDamage(this.getDamage() + ability.getDamage());
            this.getAbilities().remove(ability);
        } else {
            this.getAbilities().remove(ability);
            System.out.println("The damage is avoided, it has immunity to " + ability.getNameAbility());
        }
    }

    @Override
    public String toString() {
        String obj = "";

        obj += "Abilities: ";
        obj += abilities;
        obj += "\n";
        obj += "    {Current healt: ";
        obj += currentHealth;
        obj += ", Max healt: ";
        obj += maxHealth;
        obj += ", Current mana: ";
        obj += currentMana;
        obj += ", Max mana: ";
        obj += maxMana;
        obj += "}";
        obj += "\n";
        obj += "    {immune to fire = ";
        obj += immuneToFire;
        obj += ", immune to ice = ";
        obj += immuneToIce;
        obj += ", immune to earth = ";
        obj += immuneToEarth;
        obj += "}";

        return obj;
    }
}
