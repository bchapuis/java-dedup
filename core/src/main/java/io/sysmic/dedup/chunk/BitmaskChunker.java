package io.sysmic.dedup.chunk;

public abstract class BitmaskChunker implements Chunker {

    private int closest(int i, int l, int h) {
        return h - i < i - l ? h : l;
    }

    private int closestPowerOfTwo(int i) {
        return closest(i, Integer.highestOneBit(i), Integer.highestOneBit(i) << 1);
    }

    public int computeBitmask(int d) {
        return closestPowerOfTwo(d) - 1;
    }

}
