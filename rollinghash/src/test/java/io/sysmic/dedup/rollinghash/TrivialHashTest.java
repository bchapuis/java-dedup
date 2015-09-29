package io.sysmic.dedup.rollinghash;

public class TrivialHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new TrivialHash();
    }

}
