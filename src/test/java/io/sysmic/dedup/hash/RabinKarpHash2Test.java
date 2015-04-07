package io.sysmic.dedup.hash;

public class RabinKarpHash2Test extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RabinKarpHash2();
    }

}
