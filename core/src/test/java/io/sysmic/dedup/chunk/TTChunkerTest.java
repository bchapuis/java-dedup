package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RandomRabinKarpHash;

public class TTChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTChunker(new RandomRabinKarpHash(), 540, 460, 2800, 48);
    }

}
