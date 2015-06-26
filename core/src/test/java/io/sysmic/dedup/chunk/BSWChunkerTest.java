package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RabinHash;

public class BSWChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new BSWChunker(new RabinHash(), 540, 48);
    }

}

