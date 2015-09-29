package io.sysmic.dedup.chunker;

public class FixedSizeChunkerTest extends ChunkerTest {

    @Override
    public Chunker newChunker() {
        return new FixedSizeChunker(1000);
    }

}
