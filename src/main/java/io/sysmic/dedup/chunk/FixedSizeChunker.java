package io.sysmic.dedup.chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

/**
 * A fixed size chunker splits a byte sequence into fixed size chunks.
 */
public class FixedSizeChunker implements Chunker {

    private final int size;

    public FixedSizeChunker(int size) {
        this.size = size;
    }

    public Iterator<ByteBuffer> chunk(final ReadableByteChannel channel) {

        return new ChunkIterator(channel) {

            private ByteBuffer buffer = ByteBuffer.allocate(size);

            public ByteBuffer computeNext() {
                ByteBuffer output = null;
                try {
                    if (channel.read(buffer) != -1) {

                        // switch to read mode
                        buffer.flip();

                        // prepare the output
                        output = buffer;

                        // reset the buffer for the next iteration
                        buffer = ByteBuffer.allocate(size);

                    } else {
                        channel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return output;
            }

        };

    }

}
