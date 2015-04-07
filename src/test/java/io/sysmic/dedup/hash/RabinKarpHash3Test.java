package io.sysmic.dedup.hash;

public class RabinKarpHash3Test extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RabinKarpHash3(31);
    }

}
