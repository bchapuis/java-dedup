package io.sysmic.dedup.chunk;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

public class ChunkIterable implements Iterable<ByteBuffer> {

    private final Chunker chunker;

    private final ReadableByteChannel input;

    public ChunkIterable(Chunker chunker, ReadableByteChannel input) {
        this.chunker = chunker;
        this.input = input;
    }

    public Iterator<ByteBuffer> iterator() {
        return chunker.chunk(input);
    }

}
