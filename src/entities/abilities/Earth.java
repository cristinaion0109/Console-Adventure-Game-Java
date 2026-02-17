package entities.abilities;

import exceptions.InvalidValueException;

import java.util.Random;

public class Earth extends Spell {
    public Earth() throws InvalidValueException {
        // damage intre 30 si 40, mana intre 15 si 20
        super("Earth", new Random().nextInt(11) + 30,
                new Random().nextInt(6) + 15);
    }
}
