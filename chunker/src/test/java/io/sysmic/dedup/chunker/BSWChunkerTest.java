package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RabinKarpHash;

public class BSWChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new BSWChunker(new RabinKarpHash(), 540, 48);
    }

}

