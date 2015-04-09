package io.sysmic.dedup.hash;

public class CyclicHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new CyclicHash();
    }

}
