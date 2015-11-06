package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RabinKarpHash;

public class TTTDChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new TTTDChunker(new RabinKarpHash(), 540, 270, 460, 2800, 48);
    }

}


