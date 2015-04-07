package io.sysmic.dedup.hash;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public abstract class RollingHashTest {

    Random random = new Random(0);

    public abstract RollingHash newRollingHash();

    @Test
    public void mustRollConsistently() {

        // Initialize the first rolling hash.
        byte[] a1 = new byte[100];
        random.nextBytes(a1);
        RollingHash rh1 = newRollingHash();
        rh1.init(a1);

        // Initialize the second rolling hash.
        byte[] a2 = new byte[100];
        random.nextBytes(a2);
        RollingHash rh2 = newRollingHash();
        rh2.init(a2);

        // Update the second rolling hash
        // until it meets the first.
        for (byte b : a1) rh2.roll(b);

        assertEquals(rh1.getValue(), rh2.getValue());

    }

}
