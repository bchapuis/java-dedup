package io.sysmic.dedup.chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A utility class for extracting chunks from a byte channel.
 */
public abstract class ChunkIterator implements Iterator<ByteBuffer> {

    protected final ReadableByteChannel channel;

    private boolean initialized = false;

    private ByteBuffer next;

    protected ChunkIterator(ReadableByteChannel channel) {
        this.channel = channel;
    }

    public boolean hasNext() {
        if (!initialized) initialize();
        return next != null;
    }

    public ByteBuffer next() {
        if (!initialized) initialize();
        if (next != null) {
            ByteBuffer current = next.asReadOnlyBuffer();
            next = computeNext();
            return current;
        } else {
            throw new NoSuchElementException();
        }
    }

    private void initialize() {
        if (!initialized) {
            next = computeNext();
            initialized = true;
        }
    }

    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Computes the next chunk.
     *
     * @return A chunk if there is content to read in the channel, otherwise null.
     */
    public abstract ByteBuffer computeNext();

}
