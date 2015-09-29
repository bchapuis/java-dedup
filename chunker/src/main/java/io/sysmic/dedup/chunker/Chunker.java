package io.sysmic.dedup.chunker;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

/**
 * A chunker splits a byte channel into chunks.
 */
public abstract  class Chunker {

    public Iterator<ByteBuffer> chunk(final InputStream input) {
        return chunk(Channels.newChannel(input));
    }

    public abstract Iterator<ByteBuffer> chunk(ReadableByteChannel input);

    protected static int closest(int i, int l, int h) {
        return h - i < i - l ? h : l;
    }

    protected static int closestPowerOfTwo(int i) {
        return closest(i, Integer.highestOneBit(i), Integer.highestOneBit(i) << 1);
    }

    protected static int computeBitmask(int d) {
        return closestPowerOfTwo(d) - 1;
    }

}
