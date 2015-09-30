package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RabinHash;

public class BSWChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new BSWChunker(new RabinHash(), 540, 48);
    }

}

