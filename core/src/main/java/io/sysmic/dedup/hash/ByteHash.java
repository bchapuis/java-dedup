package io.sysmic.dedup.hash;

import java.util.Random;

public class ByteHash {

    public static final int[] array;

    static {
        Random r = new Random(0);
        array = new int[1<<8];
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.abs(r.nextInt());
        }
    }

}
