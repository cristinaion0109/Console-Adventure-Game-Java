package entities.characters;

import java.util.Random;

public class Rogue extends Character {
    public Rogue(String characterName, int experience, int level) {
        super(characterName, experience, level);
        setImmuneToEarth(true);
        setImmuneToFire(false);
        setImmuneToIce(false);
        setCharisma(new Random().nextInt(5, 11));
        setDexterity(new Random().nextInt(1, 5));
        setStrength(new Random().nextInt(1, 5));
    }

    @Override
    public void receiveDamage(int damage) {
        if(damage < (getDexterity() + getStrength())) {
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

        if(getCharisma() > 8) {
            initialDamage *= 2;
        }
        return initialDamage;
    }
}
