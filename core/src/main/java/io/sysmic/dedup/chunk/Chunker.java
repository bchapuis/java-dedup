package io.sysmic.dedup.chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A chunker splits a byte channel into chunks.
 */
public interface Chunker {

    public Iterator<ByteBuffer> chunk(ReadableByteChannel input);

}
