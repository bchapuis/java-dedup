package io.sysmic.dedup.hash;

import java.util.Arrays;
import java.util.Random;
import static io.sysmic.dedup.hash.RandomIntegers.*;

/**
 * A randomized Rabin-Karp hash function.
 */
public class RandomRabinKarpHash extends RollingHash {

    private int B = 31;

    private int BtoN = 1;

    private int h, k, l = 0;

    private byte[] buf;

    @Override
    public void reset() {
        BtoN = 1;
        h = k = l = 0;
    }

    @Override
    public void init(byte[] bytes) {
        init(bytes, 0, bytes.length);
    }

    @Override
    public void init(byte[] bytes, int off, int len) {
        reset();
        buf = Arrays.copyOfRange(bytes, off, len);
        l = len;
        for (int i = 0; i < l; i++) BtoN *= B;
        for (int i = 0; i < l; i++) h = B * h + array[buf[i] & 0xff];
    }

    @Override
    public void roll(byte in) {
        h = B * h + array[in & 0xff] - BtoN * array[buf[k] & 0xff];
        buf[k] = in;
        k = (k == l - 1) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return h;
    }

}
