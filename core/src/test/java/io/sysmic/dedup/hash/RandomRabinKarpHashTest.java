package io.sysmic.dedup.hash;

public class RandomRabinKarpHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RandomRabinKarpHash();
    }

}
