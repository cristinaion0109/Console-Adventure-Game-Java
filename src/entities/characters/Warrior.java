package entities.characters;

import java.util.Random;

public class Warrior extends Character {
    //cname, exp, level
    public Warrior(String characterName, int experience, int level) {
        super(characterName, experience, level);
        setImmuneToFire(true);
        setImmuneToIce(false);
        setImmuneToEarth(false);
        setStrength(new Random().nextInt(5, 11));
        setDexterity(new Random().nextInt(1, 5));
        setCharisma(new Random().nextInt(1, 5));
    }

    @Override
    public void receiveDamage(int damage) {
        if(damage < (getDexterity() + getCharisma())) {
           setCurrentHealth(getCurrentHealth() - damage / 2);
        }
        else {
            setCurrentHealth(getCurrentHealth() - damage);
        }
    }
    @Override
    public int getDamage() {
        Random random = new Random();

        int initialDamage = random.nextInt(7, 16);

        if(getStrength() > 8) {
            initialDamage *= 2;
        }
        return initialDamage;
    }
}
