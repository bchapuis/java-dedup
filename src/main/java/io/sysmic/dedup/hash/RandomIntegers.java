package io.sysmic.dedup.hash;

import java.util.Random;

public class RandomIntegers {

    public static final int[] array;

    static {
        Random r = new Random(0);
        array = new int[1<<8];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt();
        }
    }

}
