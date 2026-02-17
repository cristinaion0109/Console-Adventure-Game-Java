package entities.abilities;

import exceptions.InvalidValueException;

import java.util.Random;

public class Fire extends Spell {
    public Fire() throws InvalidValueException {
        // damage intre 20 si 30, mana intre 10 si 15
        super("Fire", new Random().nextInt(11) + 20,
                new Random().nextInt(6) + 10);
    }
}
