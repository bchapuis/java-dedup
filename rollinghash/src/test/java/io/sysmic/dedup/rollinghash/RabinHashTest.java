package io.sysmic.dedup.rollinghash;

public class RabinHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RabinHash();
    }

}
