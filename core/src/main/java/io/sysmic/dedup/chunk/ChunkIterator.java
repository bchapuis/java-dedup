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

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
