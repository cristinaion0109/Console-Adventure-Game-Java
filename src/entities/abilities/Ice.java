package entities.abilities;

import exceptions.InvalidValueException;

import java.util.Random;

public class Ice extends Spell {
    public Ice() throws InvalidValueException {
        // damage intre 10 si 20, mana intre 5 si 10
        super("Ice", new Random().nextInt(11) + 10,
                new Random().nextInt(6) + 5);
    }
}
