package sample;

import java.util.Random;

public class Randomizer {
    private static final Random rand = new Random();

    public static int generate() {
        int num = rand.nextInt(100);
        if (num < 99) {
            return 0;  // 90% chance of returning 0
        } else {
            return 1;  // 10% chance of returning 1
        }
    }
}

