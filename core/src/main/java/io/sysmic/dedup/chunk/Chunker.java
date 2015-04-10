package io.sysmic.dedup.chunk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A chunker splits a byte channel into chunks.
 */
public abstract  class Chunker {

    public Iterator<ByteBuffer> chunk(final InputStream input) {
        return chunk(Channels.newChannel(input));
    }

    public abstract Iterator<ByteBuffer> chunk(ReadableByteChannel input);

}
