package entities.characters;

import java.util.Random;

public class Mage extends Character {
    public Mage(String characterName, int experience, int level) {
        super(characterName, experience, level);
        setImmuneToIce(true);
        setImmuneToEarth(false);
        setImmuneToFire(false);
        setDexterity(new Random().nextInt(5, 11));
        setStrength(new Random().nextInt(1, 5));
        setCharisma(new Random().nextInt(   1,5));
    }
    @Override
    public void receiveDamage(int damage) {
        if(damage < (getStrength() + getCharisma())) {
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

        if(getDexterity() > 8) {
            initialDamage *= 2;
        }
        return initialDamage;
    }
}
