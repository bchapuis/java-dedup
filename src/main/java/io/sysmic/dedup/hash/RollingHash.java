package io.sysmic.dedup.hash;

/**
 * A rolling hash is a hash function where the input is hashed in a window that moves through the input.
 */
public abstract class RollingHash {

    public abstract void reset();

    public abstract void init(byte[] bytes);

    public abstract void init(byte[] bytes, int off, int len);

    public abstract void roll(byte in);

    public abstract int getValue();

    public static RollingHash getInstance(String algorithm) {
        if (algorithm.equals("RSync")) {
            return new RSyncHash(0);
        } else if (algorithm.equals("RabinKarp")) {
           return new RandomRabinKarpHash();
        } else if (algorithm.equals("Cyclic")) {
            return new CyclicHash();
        } else {
            return null;
        }
    }

}
