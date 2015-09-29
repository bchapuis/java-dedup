package io.sysmic.dedup.rollinghash;

public class RSyncHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new RSyncHash();
    }

}
