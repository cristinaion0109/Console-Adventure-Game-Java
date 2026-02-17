package entities.characters;

import entities.abilities.Fire;
import entities.abilities.Spell;
import exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    public Enemy() throws InvalidValueException {
        super(new Random().nextInt(80, 101), new Random().nextInt(50, 71));

        ArrayList<Spell> abilties = generateAbilities();
        setAbilities(abilties);

        setImmuneToIce(new Random().nextBoolean());
        setImmuneToFire(new Random().nextBoolean());
        setImmuneToEarth(new Random().nextBoolean());
    }


    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();

        int val = random.nextInt(2);

        switch (val) {
            case 0 :
                setCurrentHealth(getCurrentHealth() - damage);
                break;

            case 1:
                System.out.println("The enemy avoided the damage!");
                break;
            default:
                break;
        }
    }

    @Override
    public int getDamage() {
        Random random = new Random();

        int val = random.nextInt(2);
        int initialDamage = random.nextInt(1, 6);
        int finalDamage = 0;

        switch (val) {
            case 0 : 
                finalDamage = initialDamage;
                break;
                
            case 1: 
                finalDamage = 2 * initialDamage;
                break;
                
            default:
                break;
        }
        return finalDamage;
    }
}
