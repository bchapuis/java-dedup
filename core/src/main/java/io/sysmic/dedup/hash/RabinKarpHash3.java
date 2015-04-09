package io.sysmic.dedup.hash;

import java.util.Arrays;

public class RabinKarpHash3 extends RollingHash {

    private byte[] buf;

    private int h, k, l = 0;

    private final int b;

    public RabinKarpHash3(int b) {
        this.b = b;
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
        for (int i = 0; i < l; i++) {

            h += buf[i] * Math.pow(b, l - i - 1);
        }
    }

    @Override
    public void roll(byte in) {
        h = b * (h - buf[k] * (int) Math.pow(b, l-1)) + in;
        buf[k] = in;
        k = (k == l - 1) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return h;
    }

    public static void main(String... args) {
        RabinKarpHash3 rh = new RabinKarpHash3(101);

        System.out.println(Arrays.toString("abra".getBytes()));

        rh.init("abr".getBytes());

        System.out.println(rh.getValue());

        rh.roll("a".getBytes()[0]);
        rh.roll("b".getBytes()[0]);
        rh.roll("r".getBytes()[0]);

        System.out.println(rh.getValue());

    }

}
