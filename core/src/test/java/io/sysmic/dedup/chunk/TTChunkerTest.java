package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RabinHash;

public class TTChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTChunker(new RabinHash(), 540, 460, 2800, 48);
    }

}
