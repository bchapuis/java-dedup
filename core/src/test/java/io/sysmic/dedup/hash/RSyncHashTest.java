package io.sysmic.dedup.hash;

public class RSyncHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RSyncHash();
    }

}
