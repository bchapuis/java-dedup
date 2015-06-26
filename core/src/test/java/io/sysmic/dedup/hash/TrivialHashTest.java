package io.sysmic.dedup.hash;

public class TrivialHashTest extends RollingHashTest {

    @Override
    public RollingHash newRollingHash() {
        return new TrivialHash();
    }

}
