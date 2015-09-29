package io.sysmic.dedup.rollinghash;

import java.util.Arrays;

public class TrivialHash extends RollingHash {

    private byte[] buf;

    private int h, k, l = 0;

    private int d = 1;

    @Override
    public void reset() {
        h = k = l = 0;
        d = 1;
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
        for (int i = 1; i < l; i++) d = d << 1;
        for (int i = 0; i < l; i++) h = (h << 1) + buf[i];
    }

    @Override
    public void roll(byte in) {
        h = ((h - buf[k] * d) << 1) + in;
        buf[k] = in;
        k = (k == l - 1) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return h;
    }

}
