package io.sysmic.dedup.rollinghash;

import java.util.Arrays;

/**
 * A cyclic polynomial hash function (https://github.com/lemire/rollinghashjava/).
 */
public class CyclicHash extends RollingHash {

    private final int wordSize = 32;

    private int h, k, l = 0;

    private byte[] buf;

    private int fastLeftShiftL(int x) {
        return (x << l ) | (x >>> (wordSize - l));
    }

    private int fastLeftShift1(int x) {
        return (x << 1 ) | (x >>> (wordSize - 1));
    }

    @Override
    public void reset() {
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
        for (int i = 0; i < l; i++) h = fastLeftShift1(h) ^ ByteHash.array[buf[i] & 0xff];
    }

    @Override
    public void roll(byte in) {
        h =  fastLeftShift1(h) ^ fastLeftShiftL(ByteHash.array[buf[k] & 0xff]) ^ ByteHash.array[in & 0xff];
        buf[k] = in;
        k = (k == l - 1) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return h;
    }

}
