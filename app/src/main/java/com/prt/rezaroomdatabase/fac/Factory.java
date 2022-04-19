package com.prt.rezaroomdatabase.fac;

import java.util.Random;
import java.util.UUID;

public class Factory {

    private static final Random random = new Random();

    public static long randomLong() {
        return random.nextLong();
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}
