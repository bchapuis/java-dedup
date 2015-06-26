package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RabinHash;

public class TTTDChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTTDChunker(new RabinHash(), 540, 270, 460, 2800, 48);
    }

}


