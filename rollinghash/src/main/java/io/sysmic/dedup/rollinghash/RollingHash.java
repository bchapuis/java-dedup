package io.sysmic.dedup.rollinghash;

import java.security.NoSuchAlgorithmException;

/**
 * A rolling hash is a hash function where the input is hashed in a window that moves through the input.
 */
public abstract class RollingHash {

    public abstract void reset();

    public abstract void init(byte[] bytes);

    public abstract void init(byte[] bytes, int off, int len);

    public abstract void roll(byte in);

    public abstract int getValue();

    public static RollingHash getInstance(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm.equals("FastRabinKarp")) {
            return new RabinHash();
        } else if (algorithm.equals("RandomRabinKarp")) {
           return new RabinHash();
        } else if (algorithm.equals("Cyclic")) {
            return new CyclicHash();
        } else if (algorithm.equals("RSync")) {
            return new RSyncHash(0);
        } else {
            throw new NoSuchAlgorithmException();
        }
    }

}
