package io.sysmic.dedup.chunker;

import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * A utility class for extracting chunks from a byte channel.
 */
public abstract class ChunkIterator implements Iterator<ByteBuffer> {

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
