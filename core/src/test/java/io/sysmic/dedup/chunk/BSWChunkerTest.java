package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RandomRabinKarpHash;

public class BSWChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new BSWChunker(new RandomRabinKarpHash(), 540, 48);
    }

}

