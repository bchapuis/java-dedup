package io.sysmic.dedup.hash;

public class RabinKarpHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RabinKarpHash();
    }

}
