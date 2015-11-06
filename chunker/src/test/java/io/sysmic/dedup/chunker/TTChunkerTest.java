package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RabinKarpHash;

public class TTChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTChunker(new RabinKarpHash(), 540, 460, 2800, 48);
    }

}
