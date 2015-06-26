package io.sysmic.dedup.hash;

public class RabinHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RabinHash();
    }

}
