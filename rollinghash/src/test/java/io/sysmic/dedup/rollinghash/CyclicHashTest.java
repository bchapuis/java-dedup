package io.sysmic.dedup.rollinghash;

public class CyclicHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new CyclicHash();
    }

}
