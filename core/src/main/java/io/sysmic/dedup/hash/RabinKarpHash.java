package io.sysmic.dedup.hash;

import java.util.Arrays;

public class RabinKarpHash extends RollingHash {

    private byte[] buf;

    private int h, k, l = 0;

    private final int d;

    public RabinKarpHash(int d) {
        this.d = d;
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
        for (int i = l - 1; i >= 0; i--) {
            h += bytes[i] * d;
        }
    }

    @Override
    public void roll(byte in) {
        h -= buf[k] * d;
        h += in * d;
        buf[k] = in;
        k = (k == l - 1) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return h;
    }

}
