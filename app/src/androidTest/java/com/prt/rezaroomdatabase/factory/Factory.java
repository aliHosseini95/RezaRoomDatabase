package com.prt.rezaroomdatabase.factory;

import java.util.Random;
import java.util.UUID;

public class Factory {

    private static Random random = new Random();

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
