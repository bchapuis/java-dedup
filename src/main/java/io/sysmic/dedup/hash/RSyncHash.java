package io.sysmic.dedup.hash;

import java.util.Arrays;

/**
 * An Adler32 inspired rolling checksum ported from RSync (see checksum.c and match.c).
 */
public class RSyncHash extends RollingHash {

    private int s1, s2, k, l = 0;

    private byte[] buf;

    private final int charOffset;

    public RSyncHash() {
        this(0);
    }

    public RSyncHash(int charOffset) {
       this.charOffset = charOffset;
    }

    @Override
    public void reset() {
       s1 = s2 = k = l = 0;
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
        int i = 0;
        while (i < l - 4) {
            s2 += 4 * (s1 + buf[i]) + 3 * buf[i+1] + 2 * buf[i+2] + buf[i+3] + 10 * charOffset;
            s1 += buf[i] + buf[i+1] + buf[i+2] + buf[i+3] + 4 * charOffset;
            i += 4;
        }
        while (i < l) {
            s1 += buf[i] + charOffset;
            s2 += s1;
            i += 1;
        }
    }

    @Override
    public void roll(byte in) {
        s1 -= buf[k] + charOffset;
        s2 -= l * (buf[k] + charOffset);
        s1 += in + charOffset;
        s2 += s1;
        buf[k] = in;
        k = (k >= l) ? 0 : k + 1;
    }

    @Override
    public int getValue() {
        return (s1 & 0xffff) + (s2 << 16);
    }

}
