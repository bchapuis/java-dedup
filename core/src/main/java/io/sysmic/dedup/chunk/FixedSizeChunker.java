package io.sysmic.dedup.chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A fixed size chunker splits a byte sequence into fixed size chunks.
 */
public class FixedSizeChunker implements Chunker {

    private final int size;

    public FixedSizeChunker(int size) {
        this.size = size;
    }

    public Iterator<ByteBuffer> chunk(final ReadableByteChannel channel) {

        return new ChunkIterator() {

            private ByteBuffer buffer = ByteBuffer.allocate(size);

            public boolean hasNext() {
                try {
                    channel.read(buffer);
                    buffer.flip();
                    return buffer.hasRemaining();
                } catch (IOException e) {
                    return false;
                } finally {
                    buffer.compact();
                }
            }

            public ByteBuffer next() {
                try {
                    channel.read(buffer);
                    buffer.flip();
                    return buffer.asReadOnlyBuffer();
                } catch (IOException e) {
                    throw new NoSuchElementException();
                } finally {
                    buffer = ByteBuffer.allocate(size);
                }
            }

        };

    }

}
