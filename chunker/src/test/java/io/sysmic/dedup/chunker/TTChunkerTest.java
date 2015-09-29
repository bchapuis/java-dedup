package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RabinHash;

public class TTChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTChunker(new RabinHash(), 540, 460, 2800, 48);
    }

}
