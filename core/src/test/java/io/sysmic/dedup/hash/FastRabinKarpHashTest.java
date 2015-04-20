package io.sysmic.dedup.hash;

public class FastRabinKarpHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new FastRabinKarpHash();
    }

}
