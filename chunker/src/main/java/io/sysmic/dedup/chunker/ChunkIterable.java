package io.sysmic.dedup.chunker;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

public class ChunkIterable implements Iterable<ByteBuffer> {

    private final Chunker chunker;

    private final ReadableByteChannel input;

    public ChunkIterable(Chunker chunker, ReadableByteChannel input) {
        this.chunker = chunker;
        this.input = input;
    }

    public ChunkIterable(Chunker chunker, InputStream input) {
        this.chunker = chunker;
        this.input = Channels.newChannel(input);
    }

    public Iterator<ByteBuffer> iterator() {
        return chunker.chunk(input);
    }

}
